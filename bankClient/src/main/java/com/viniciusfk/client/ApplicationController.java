package com.viniciusfk.client;

import com.viniciusfk.client.dataTransfer.RequestResult;
import com.viniciusfk.client.utility.Validate;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static com.viniciusfk.client.service.LoginService.login;


public class ApplicationController {
    private static final String apiUrl = System.getenv("BANK_API_URL");

    //-----------START OF LOGIN PAGE----------//

    @FXML
    public Button signInButton;

    @FXML
    private Pane loginPane;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField cpfField;

    @FXML
    public Label loginInfo;

    @FXML
    void goToSignUp(ActionEvent event) {
        loginPane.visibleProperty().set(false);
    }

    @FXML
    void signIn(ActionEvent event) throws IOException, InterruptedException {
        signInButton.setDisable(true);

        Validate validate = new Validate();
        validate.validateCPF(cpfField.getText());

        if(validate.isValid()){
            RequestResult responseResult = login(cpfField.getText(), passwordField.getText());

            if(responseResult.result()){
                loginPane.visibleProperty().set(false);
            }else{
                loginInfo.setText(responseResult.message());
            }
        }else{
            loginInfo.setText(validate.getMessage());
        }

        signInButton.setDisable(false);
    }

    //------------END OF LOGIN PAGE-----------//



    //-----------START OF SIGNUP PAGE---------//

    //------------END OF SIGNUP PAGE----------//



    //------------START OF MAIN PAGE----------//

    @FXML
    private Text welcomeText;

    @FXML
    private Text availableBalance;

    //-------------END OF MAIN PAGE-----------//
}

