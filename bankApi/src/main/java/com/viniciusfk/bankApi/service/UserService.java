package com.viniciusfk.bankApi.service;

import com.viniciusfk.bankApi.model.User;
import com.viniciusfk.bankApi.repository.user.IUser;
import com.viniciusfk.bankApi.security.Token;
import com.viniciusfk.bankApi.security.TokenUtil;
import com.viniciusfk.bankApi.dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IUser repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUser repository){
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser (User user) {
        String encoder = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encoder);
        return repository.save(user);
    }

    public Boolean validatePassword(User user) {
        String password = repository.getReferenceById(user.getId()).getPassword();
        return passwordEncoder.matches(user.getPassword(), password);
    }

    public Token generateToken(UserDto user) {
        User person = repository.findByNameOrCpf(user.getName(), user.getCpf());
        if(person != null){
            boolean valid = passwordEncoder.matches(user.getPassword(), person.getPassword());

            if(valid){
                return new Token(TokenUtil.createToken(person));
            }
        }
        return null;
    }
}