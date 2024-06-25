package com.viniciusfk.bankApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto extends UserDto {
    private double ammount;

    public TransactionDto(String name, String password, String cpf, String phone, double ammount) {
        super(name, password, cpf, phone);
        this.ammount = ammount;
    }
}
