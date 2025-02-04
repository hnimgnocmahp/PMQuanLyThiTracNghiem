package org.example.phanmemthitracnghiem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class LoginController {
    @FXML
    private PasswordField confirmPassword_register;

    @FXML
    private TextField email_login;

    @FXML
    private TextField email_register;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Line line;

    @FXML
    private Button login;

    @FXML
    private Button login_register;

    @FXML
    private PasswordField password_login;

    @FXML
    private PasswordField password_register;

    @FXML
    private Label register;

    @FXML
    private CheckBox showPassword;

    @FXML
    private Button signin;

    @FXML
    void toRegPage(MouseEvent event) {
        email_login.setVisible(false);
        password_login.setVisible(false);
        register.setVisible(false);
        showPassword.setVisible(false);
        login.setVisible(false);
        line.setVisible(false);
        email_register.setVisible(true);

        password_register.setVisible(true);
        confirmPassword_register.setVisible(true);
        firstName.setVisible(true);
        lastName.setVisible(true);
        signin.setVisible(true);
        login_register.setVisible(true);
    }

    @FXML
    void toLogPage(MouseEvent event) {
        email_login.setVisible(true);
        password_login.setVisible(true);
        register.setVisible(true);
        showPassword.setVisible(true);
        login.setVisible(true);
        line.setVisible(true);

        email_register.setVisible(false);
        password_register.setVisible(false);
        confirmPassword_register.setVisible(false);
        firstName.setVisible(false);
        lastName.setVisible(false);
        signin.setVisible(false);
        login_register.setVisible(false);
    }

    @FXML
    public void initialize() {
        email_register.setVisible(false);
        password_register.setVisible(false);
        confirmPassword_register.setVisible(false);
        firstName.setVisible(false);
        lastName.setVisible(false);
        signin.setVisible(false);
        login_register.setVisible(false);
    }


}