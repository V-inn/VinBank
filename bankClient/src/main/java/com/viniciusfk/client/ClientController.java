package com.viniciusfk.client;

import com.viniciusfk.client.services.Validate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ClientController {
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
    void signIn(ActionEvent event) {
        Validate validate = new Validate();
        validate.validateCPF(cpfField.getText());

        if(validate.isValid()){
            loginPane.visibleProperty().set(false);
        }else{
            cpfInfo.setText(validate.getMessage());
        }
    }

}

