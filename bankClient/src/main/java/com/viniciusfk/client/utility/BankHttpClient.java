package com.viniciusfk.client.utility;

import java.net.http.HttpClient;
import java.time.Duration;

public class BankHttpClient {
    private static final String apiUrl = "http://localhost:8080/";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private BankHttpClient() {
        // Private constructor to prevent external instantiation
    }

    public static String getApiUrl(){
        return apiUrl;
    }

    public static HttpClient getSharedClient() {
        return httpClient;
    }
}
