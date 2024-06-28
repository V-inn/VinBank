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

public class LoginService {
    public static RequestResult login(String cpf, String password) throws IOException, InterruptedException {
        String parsed = "{\"cpf\": \"" + cpf + "\", \"password\": \"" + password + "\"}";

        HttpClient client = BankHttpClient.getSharedClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BankHttpClient.getApiUrl() + "/users/login"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(parsed))
                .build();

        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (IOException e){
            return  new RequestResult(false, "Erro de conexão com o servidor.");
        }catch (InterruptedException e){
            return  new RequestResult(false, "A solicitação foi interrompida.");
        }

        if(response.statusCode() == 200){
            List<String> cookies = response.headers().map().get("Set-Cookie");
            CookieManagerUtility.addCookies(cookies);
            System.out.println(CookieManagerUtility.getCookies());
            return new RequestResult(true, response.body());
        }
        return new RequestResult(false, response.body());
    }
}
