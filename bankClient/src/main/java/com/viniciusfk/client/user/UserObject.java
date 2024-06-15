package com.viniciusfk.client.user;

import javax.swing.*;

public class UserObject {
    private final String name;
    private final double cpf;
    private final String password;


    public UserObject(String name, double cpf, String password) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
    }

    public double getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
