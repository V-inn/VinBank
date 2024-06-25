package com.viniciusfk.bankApi.controller;

import com.viniciusfk.bankApi.dto.TransactionDto;
import com.viniciusfk.bankApi.records.ResponseResult;
import com.viniciusfk.bankApi.security.TokenUtil;
import com.viniciusfk.bankApi.service.BankingService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@RestController
@RequestMapping("/bank")
public class BankingController {
    private final BankingService bankingService;

    private BankingController(BankingService bankingService){
        this.bankingService = bankingService;
    }

    // Método privado comum para deposit e withdraw
    private ResponseEntity<?> performOperation(String userCookie, String amount, BiFunction<String, Double, ResponseResult> transactionFunction) {
        double quantity;
        try{
            quantity = Double.parseDouble(amount);
        }catch (NumberFormatException e){
            return ResponseEntity.status(400).body("Amount is not a number");
        }

        if (TokenUtil.validateToken(userCookie)) {
            String origin = TokenUtil.getSubject(userCookie);
            ResponseResult responseResult = transactionFunction.apply(origin, quantity);
            return ResponseEntity.status(200).body(responseResult.message());
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@Valid @CookieValue(name = "token") String userCookie, @RequestBody String amount) {
        return performOperation(userCookie, amount, bankingService::deposit);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@Valid @CookieValue(name = "token") String userCookie, @RequestBody String amount) {
        return performOperation(userCookie, amount, bankingService::withdraw);
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@Valid @CookieValue(name = "token") String userCookie, @RequestBody TransactionDto transactionParameters, HttpServletResponse response){
        if(TokenUtil.validateToken(userCookie)){
            String origin = TokenUtil.getSubject(userCookie);
            ResponseResult responseResult = bankingService.attemptTransaction(origin, transactionParameters);

            if(responseResult.result()){
                return ResponseEntity.status(200).body(responseResult.message());
            }
            return ResponseEntity.status(400).body(responseResult.message());
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> balance(@Valid @CookieValue(name = "token") String userCookie){
        if(TokenUtil.validateToken(userCookie)){
            return ResponseEntity.status(200).body(bankingService.getOwnBalance(TokenUtil.getSubject(userCookie)));
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
