package com.viniciusfk.client;

import com.viniciusfk.client.dataTransfer.RequestResult;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.viniciusfk.client.service.LoginService.login;
import static com.viniciusfk.client.service.SignupService.signup;

import static com.viniciusfk.client.utility.Validate.*;


public class ApplicationController {
    protected static final String apiUrl = System.getenv("BANK_API_URL");

    //-----------START OF LOGIN PAGE----------//

    @FXML
    protected Button loginButton;

    @FXML
    protected Pane loginPane;

    @FXML
    protected PasswordField loginPasswordField;

    @FXML
    protected TextField loginCpfField;

    @FXML
    protected Label loginInfo;

    @FXML
    void goToSignUp() {
        loginPane.visibleProperty().set(false);
        signupPane.visibleProperty().set(true);

    }

    @FXML
    void signIn() throws IOException, InterruptedException {
        System.out.println(apiUrl);

        loginButton.setDisable(true);

        RequestResult isCpfValid = validateCPF(loginCpfField.getText());

        if(isCpfValid.result()){
            RequestResult responseResult = login(loginCpfField.getText(), loginPasswordField.getText());
            System.out.println(responseResult.result() + " | " + responseResult.message());
            if(responseResult.result()){
                loginPane.setVisible(false);
                mainPane.setVisible(true);
            }else{
                loginInfo.setText(responseResult.message());
            }
        }else{
            loginInfo.setText(isCpfValid.message());
            loginCpfField.setText("");
        }

        loginButton.setDisable(false);
    }

    //------------END OF LOGIN PAGE-----------//



    //-----------START OF SIGNUP PAGE---------//

    @FXML
    protected Pane signupPane;

    @FXML
    protected Text signupInfo;

    @FXML
    protected TextField signupNameField;

    @FXML
    protected TextField signupCpfField;

    @FXML
    protected TextField signupPhoneField;

    @FXML
    protected TextField signupPasswordField;

    @FXML
    protected Button signupButton;

    @FXML
    private Text signupPasswordRequirementOne;

    @FXML
    private Text signupPasswordRequirementTwo;

    @FXML
    private Text signupPasswordRequirementThree;

    @FXML
    private Text signupPasswordRequirementFour;



    public void signUp(ActionEvent actionEvent) throws IOException, InterruptedException {
        System.out.println("Signup began.");

        signupButton.setDisable(true);

        RequestResult isValidName = validateName(signupNameField.getText());
        RequestResult isValidCpf = validateCPF(signupCpfField.getText());
        RequestResult isValidPhone = validatePhoneNumber(signupPhoneField.getText());
        RequestResult isValidPassword = validatePasswordByDefaultParameters(signupPasswordField.getText());

        if(!isValidName.result()){
            signupInfo.setText(isValidName.message());
        }else if(!isValidCpf.result()){
            signupInfo.setText(isValidCpf.message());
        }else if(!isValidPhone.result()){
            signupInfo.setText(isValidPhone.message());
        }else if(!isValidPassword.result()){
            signupInfo.setText(isValidPassword.message());
        }else{
            RequestResult signup = signup(signupNameField.getText(), signupCpfField.getText(), signupPhoneField.getText(), signupPasswordField.getText());

            if (signup.result()){
                System.out.println("Successfully created an account.");

                signupPane.setVisible(false);
                mainPane.setVisible(true);
            }else {

                System.out.println("Signup failed due to bad request: " + signup.errorCode() + " : " + signup.errorType());
                signupInfo.setText(signup.message());
            }
        }

        signupButton.setDisable(false);
    }

    public void checkIfValidPassword() {
        RequestResult isValidPassword = validatePasswordByDefaultParameters(signupPasswordField.getText());
        signupPasswordRequirementOne.setFill(new Color(0,0,0,1));
        signupPasswordRequirementTwo.setFill(new Color(0,0,0,1));
        signupPasswordRequirementThree.setFill(new Color(0,0,0,1));
        signupPasswordRequirementFour.setFill(new Color(0,0,0,1));

        switch (isValidPassword.errorCode()){
            case 1:
                signupInfo.setText(isValidPassword.message());
                break;
            case 2:
                signupPasswordRequirementOne.setFill(new Color(1,0,0,1));
                break;
            case 3:
                signupPasswordRequirementTwo.setFill(new Color(1,0,0,1));
                break;
            case 4:
                signupPasswordRequirementThree.setFill(new Color(1,0,0,1));
                break;
            case 5:
                signupPasswordRequirementFour.setFill(new Color(1,0,0,1));
                break;
            default:
                break;
        }
    }

    //------------END OF SIGNUP PAGE----------//



    //------------START OF MAIN PAGE----------//

    @FXML
    protected Pane mainPane;

    @FXML
    protected Text welcomeText;

    @FXML
    protected Text availableBalance;

    //-------------END OF MAIN PAGE-----------//
}

