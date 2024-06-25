package com.viniciusfk.client.utility;

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

        if(cpf.matches("([1234567890])\\1{10}")){
            this.message = "CPF inválido.";
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

            currentMultiplier--;
        }

        firstDigit = ((sum%11) <= 1) ? 0 : 11-(sum%11);

        //Checks if first verifier digit is equal as to inputted
        if (firstDigit != Integer.parseInt(digits[9])){
            return false;
        }

        for (int i = 0; i < digits.length-2; i++) {
            sum += Integer.parseInt(digits[i]);
        }

        sum+= Integer.parseInt(digits[9])*2;

        secondDigit = ((sum%11) <= 1) ? 0 : 11-(sum%11);

        //Checks if second verifier digit is equal as to inputted and if it is return valid CPF
        return secondDigit == Integer.parseInt(digits[10]);
    }
}
