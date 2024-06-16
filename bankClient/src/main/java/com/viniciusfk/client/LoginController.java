package com.viniciusfk.client;

import com.viniciusfk.client.services.Validate;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;


public class LoginController {
    @FXML
    private Pane loginPane;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField cpfField;

    @FXML
    public Label cpfInfo;

    @FXML
    public Label passwordInfo;

    @FXML
    void goToSignUp(ActionEvent event) {
        loginPane.visibleProperty().set(false);
    }

    @FXML
    void signIn(ActionEvent event) throws IOException, InterruptedException {
        Validate validate = new Validate();
        validate.validateCPF(cpfField.getText());

        if(validate.isValid()){
            if(sendLoginRequest()){
                loginPane.visibleProperty().set(true);
            }
        }else{
            cpfInfo.setText(validate.getMessage());
        }
    }

    private boolean sendLoginRequest() throws IOException, InterruptedException {
        String responseBody = sendGetRequest("http://localhost:8080/users/hello");
        System.out.println(responseBody);
        cpfInfo.setText(responseBody);

        return true;
    }

    public static String sendGetRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

}

