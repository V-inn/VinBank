package com.viniciusfk.client;

import com.viniciusfk.client.utility.BankHttpClient;
import com.viniciusfk.client.utility.CookieManagerUtility;
import com.viniciusfk.client.utility.Validate;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static com.viniciusfk.client.service.LoginService.login;


public class ApplicationController {
    private static final String apiUrl = System.getenv("BANK_API_URL");

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
            if(login(cpfField.getText(), passwordField.getText())){
                loginPane.visibleProperty().set(false);
            }else{

            }
        }else{
            cpfInfo.setText(validate.getMessage());
        }
    }

    private boolean testRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/users/hello"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        System.out.println(response.body());
        cpfInfo.setText(response.body());

        return true;
    }

}

