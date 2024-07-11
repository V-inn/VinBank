package com.viniciusfk.client.service;

import com.viniciusfk.client.dataTransfer.RequestResult;
import com.viniciusfk.client.utility.BankHttpClient;
import com.viniciusfk.client.utility.CookieManagerUtility;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static com.viniciusfk.client.service.LoginService.login;

public class SignupService {
    public static RequestResult signup(String name, String cpf, String phone, String password) throws IOException, InterruptedException {
        String parsed = "{\"name\": \"" + name + "\", " +
                "\"cpf\": \"" + cpf + "\", " +
                "\"phone\": \"" + phone + "\", " +
                "\"password\": \"" + password + "\"}";

        HttpClient client = BankHttpClient.getSharedClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BankHttpClient.getApiUrl() + "/users"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(parsed))
                .build();

        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (IOException e){
            return  new RequestResult(false, "Erro de conexão com o servidor.", "connectionError", (byte) 1);
        }catch (InterruptedException e){
            return  new RequestResult(false, "A solicitação foi interrompida.", "connectionError", (byte) 2);
        }

        if(response.statusCode() == 201){
            RequestResult login = login(cpf, password);
            if(!login.result()){
                return login;
            }
            return new RequestResult(true, response.body(), null, null);
        }
        //TODO: Add more specific error messages.
        return new RequestResult(false, response.body(), "accountCreationError", null);
    }
}
