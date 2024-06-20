package com.viniciusfk.bankApi.controller;

import com.viniciusfk.bankApi.model.Transaction;
import com.viniciusfk.bankApi.model.user.User;
import com.viniciusfk.bankApi.security.Token;
import com.viniciusfk.bankApi.security.TokenUtil;
import com.viniciusfk.bankApi.service.UserService;
import com.viniciusfk.bankApi.userDto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @PostMapping("/login")
    public  ResponseEntity<Token> login(@Valid @RequestBody UserDto user, HttpServletResponse response) throws IOException {
        Token token = userService.generateToken(user);
        if(token != null){
            Cookie cookie = new Cookie("token", token.getToken());
            System.out.println(token.getToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            // Add cookie to response
            response.addCookie(cookie);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.status(200).body("Hello");
    }

    @GetMapping("/authenticated")
    public  ResponseEntity<?> authenticated(){
        return ResponseEntity.status(200).build();
    }

    //TODO: add DTO with UserDto and ammount
    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@Valid @CookieValue(name = "token") String userCookie, @RequestBody UserDto user, int ammount, HttpServletResponse response){
        if(TokenUtil.validateToken(userCookie)){
            String origin = TokenUtil.getSubject(userCookie);
            Transaction transaction = userService.attemptTransaction(origin, user, ammount);
            return ResponseEntity.status(200).body(transaction.message());
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> balance(@Valid @CookieValue(name = "token") String userCookie){
        if(TokenUtil.validateToken(userCookie)){
            return ResponseEntity.status(200).body(userService.getOwnBalance(TokenUtil.getSubject(userCookie)));
        }
        return ResponseEntity.status(401).build();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
