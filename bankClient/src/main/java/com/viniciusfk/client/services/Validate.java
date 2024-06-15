package com.viniciusfk.client.services;

import java.util.Arrays;

public class Validate {
    private boolean result = false;
    private String message = "";

    public void validateCPF(String cpf){
        if(cpf.isBlank()){
            this.message = "CPF é um campo obrigatório.";
            return;
        }

        if(!cpf.matches("\\d+")){
            this.message = "CPF deve conter apenas números.";
            return;
        }

        if(cpf.length() != 11){
            this.message = "CPF deve conter 11 digitos.";
            return;
        }

        if (!isValidCPF(cpf)){
            this.message = "CPF inválido.";
            return;
        }

        result = true;
    }

    public boolean isValid() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    private boolean isValidCPF(String cpf){
        String[] digits = cpf.split("");
        int sum = 0;
        int firstDigit;
        int secondDigit;
        byte currentMultiplier = 10;

        for (int i = 0; i < cpf.length()-2; i++) {
            sum += Integer.parseInt(digits[i]) * currentMultiplier;

            System.out.println(sum);

            currentMultiplier--;
        }

        firstDigit = ((sum%11) <= 1) ? 0 : 11-(sum%11);

        System.out.println("----------------------------------");
        System.out.println(firstDigit);
        System.out.println("----------------------------------");

        //Checks if first verifier digit is equal as to inputted
        if (firstDigit != Integer.parseInt(digits[9])){
            return false;
        }

        for (int i = 0; i < digits.length-1; i++) {
            sum += Integer.parseInt(digits[i]);
            System.out.println(sum);
        }

        secondDigit = ((sum%11) <= 1) ? 0 : 11-(sum%11);

        System.out.println("----------------------------------");
        System.out.println(secondDigit);
        System.out.println("----------------------------------");


        //Checks if second verifier digit is equal as to inputted and if it is return valid CPF
        return secondDigit == Integer.parseInt(digits[10]);
    }
}
