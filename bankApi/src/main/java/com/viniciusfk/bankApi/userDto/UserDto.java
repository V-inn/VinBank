package com.viniciusfk.bankApi.userDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String name;
    private String password;
    private String cpf;
    private String phone;
    private int balance;

    public UserDto(String name, String password, String cpf, String phone){
        super();
        this.name = name;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.balance = 0;
    }
}
