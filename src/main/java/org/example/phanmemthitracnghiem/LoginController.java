package org.example.phanmemthitracnghiem;

import BUS.UserBUS;
import DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import jdk.jfr.Event;

import java.awt.event.ActionEvent;
import java.io.IOException;

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String email = email_login.getText();
            String password = password_login.getText();
            UserDTO userDTO = UserBUS.getInstance().findUserByEmail(email);

            if (userDTO != null && userDTO.getPassword().equals(password)) {
                switchToAdmin(event); // Truyền event vào switchToAdmin
            } else {
                showAlert("Lỗi", "Email hoặc mật khẩu không đúng!");
            }
        }

    }

    @FXML
    void click(MouseEvent event) {
        String email = email_login.getText();
        String password = password_login.getText();
        UserDTO userDTO = UserBUS.getInstance().findUserByEmail(email);

        if (userDTO != null && userDTO.getPassword().equals(password)) {
            switchToAdmin(event); // Truyền event vào switchToAdmin
        } else {
            showAlert("Lỗi", "Email hoặc mật khẩu không đúng!");
        }
    }

    private void switchToAdmin(MouseEvent event) {
        try {
            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tải Admin.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();

            // Tạo Scene mới
            Scene scene = new Scene(root, 1024, 600);

            // Cập nhật Stage hiện tại
            stage.setTitle("Phần mềm thi trắc nghiệm");
            stage.setScene(scene);
            // Căn giữa màn hình
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tải giao diện Admin!");
        }
    }

    private void switchToAdmin(KeyEvent event) {
        try {
            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tải Admin.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();

            // Tạo Scene mới
            Scene scene = new Scene(root, 1024, 600);

            // Cập nhật Stage hiện tại
            stage.setTitle("Phần mềm thi trắc nghiệm");
            stage.setScene(scene);
            // Căn giữa màn hình
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tải giao diện Admin!");
        }
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