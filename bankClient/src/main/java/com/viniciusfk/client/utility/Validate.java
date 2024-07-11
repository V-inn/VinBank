package com.viniciusfk.client.utility;

import com.viniciusfk.client.dataTransfer.RequestResult;

//TODO: Make validation server-side only.

public class Validate {
    public static RequestResult validateName(String name){
        return new RequestResult(true, "Nome válido", null, null);
    }

    public static RequestResult validateCPF(String cpf){
        if(cpf.isBlank()){
            return new RequestResult(false, "CPF é um campo obrigatório.","cpfError", (byte) 1);
        }

        if(!cpf.matches("\\d+")){
            return new RequestResult(false, "CPF deve conter apenas números." ,"cpfError", (byte) 2);
        }

        if(cpf.length() != 11){
            return new RequestResult(false, "CPF deve conter 11 digitos." , "cpfError", (byte) 3);
        }

        if(cpf.matches("([1234567890])\\1{10}")){
            return new RequestResult(false, "CPF inválido." ,"cpfError", (byte) 4);
        }

        if (!isValidCPF(cpf)){
            return new RequestResult(false, "CPF inválido." , "cpfError", (byte) 4);
        }

        return new RequestResult(true, "CPF válido", null, null);
    }

    public static RequestResult validatePasswordByDefaultParameters(String password){
        if (password.matches("[<>'\"(){}:;]")){
            return new RequestResult(false, "A senha não pode conter os seguintes digitos: <>'\"(){}:;", "badPassword" , (byte) 1);
        }

        if(password.length() < 8){
            return new RequestResult(false, "A senha precisa ter no mínimo 8 digitos.", "badPassword", (byte) 2);
        }

        if(!(password.matches("(.*[a-z].*)") && password.matches("(.*[A-Z].*)"))){
            return new RequestResult(false, "A senha deve conter no mínimo um caractér maiusculo e um minúsculo", "badPassword", (byte) 3);
        }

        if(!password.matches(".*[!@#$%&].*")){
            return new RequestResult(false, "A senha deve conter no mínimo um caractér especial", "badPassword", (byte) 4);
        }

        if(!password.matches(".*[0-9].*")){
            return new RequestResult(false, "A senha deve conter no mínimo um número", "badPassword", (byte) 5);
        }

        return new RequestResult(true, "Senha válida", null, null);
    }

    public static RequestResult validatePhoneNumber(String phone){
        return new RequestResult(true, "Telefone válido", null, null);
    }

    private static boolean isValidCPF(String cpf){
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
