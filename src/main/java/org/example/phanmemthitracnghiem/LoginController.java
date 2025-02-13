package org.example.phanmemthitracnghiem;

import BUS.UserBUS;
import DTO.UserDTO;
import javafx.beans.binding.Bindings;
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

import javax.naming.Binding;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
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
    private Label lbl_passwordShowed;

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

    // Hàm truy cập trang đăng kí
    void toRegPagePublic() {
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

    // Hàm truy cập trang đăng kí
    @FXML
    void toRegPage(MouseEvent event) {
        toRegPagePublic();
    }

    void toLogPagePublic() {
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
    // Hàm truy cập trang đăng nhập
    @FXML
    void toLogPage(MouseEvent event) {
        toLogPagePublic();
    }


    // Hàm thông báo
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Hàm đăng nhập
    @FXML
    void enterLogin(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String email = email_login.getText();
            String password = password_login.getText();
            UserDTO userDTO = UserBUS.getInstance().findUserByEmail(email);

            if (userDTO != null && userDTO.getPassword().equals(password) &&userDTO.getRole()==0) {
                switchToAdmin(event); // Truyền event vào switchToAdmin
            }else if (userDTO != null && userDTO.getPassword().equals(password) &&userDTO.getRole()==1) {
                switchToGuest(event);
            } else {
                showAlert("Lỗi", "Email hoặc mật khẩu không đúng!");
            }
        }
    }

    // Hàm đăng nhập
    @FXML
    void clickLogin(MouseEvent event) {
        String email = email_login.getText();
        String password = password_login.getText();
        UserDTO userDTO = UserBUS.getInstance().findUserByEmail(email);

        if (userDTO != null && userDTO.getPassword().equals(password) &&userDTO.getRole()==0) {
            switchToAdmin(event); // Truyền event vào switchToAdmin
        }else if (userDTO != null && userDTO.getPassword().equals(password) &&userDTO.getRole()==1) {
            switchToGuest(event);
        } else {
            showAlert("Lỗi", "Email hoặc mật khẩu không đúng!");
        }
    }

    // Hàm đăng kí
    @FXML
    void register(MouseEvent event) {
        String ln = lastName.getText();
        String fn = firstName.getText();
        String email = email_register.getText();
        String password = password_register.getText();
        String confirmPassword = confirmPassword_register.getText();

        if (password.equals(confirmPassword)) {
            UserDTO userDTO = new UserDTO(ln, fn, email, password, 1, 1);
            int i = UserBUS.getInstance().addUser(userDTO);
            if (i > 0) {
                toLogPagePublic();
                lastName.setText(null);
                firstName.setText(null);
                email_register.setText(null);
                password_register.setText(null);
                confirmPassword_register.setText(null);
            }
        }
    }

    // Hàm đánh dấu checkbox
    @FXML
    void passwordShowCheckBox(MouseEvent event) {
        if (showPassword.isSelected()) {
            lbl_passwordShowed.setVisible(true);
            lbl_passwordShowed.setText(password_login.getText());
        } else {
            lbl_passwordShowed.setVisible(false);
        }
    }

    // Hàm truy cập trang admin
    private void switchToAdmin(MouseEvent event) {
        try {
            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tải Admin.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();

            // Tạo Scene mới
            Scene scene = new Scene(root);

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

    // Hàm truy cập trang admin
    private void switchToAdmin(KeyEvent event) {
        try {
            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tải Admin.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();

            // Tạo Scene mới
            Scene scene = new Scene(root);

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

    // Hàm truy cập trang guest
    private void switchToGuest(KeyEvent event) {
        try {
            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tải Admin.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Guest.fxml"));
            Parent root = loader.load();

            // Tạo Scene mới
            Scene scene = new Scene(root);

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

    // Hàm truy cập trang guest
    private void switchToGuest(MouseEvent event) {
        try {
            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tải Admin.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Guest.fxml"));
            Parent root = loader.load();

            // Tạo Scene mới
            Scene scene = new Scene(root);

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

        password_login.textProperty().addListener((observable, oldValue, newValue) -> {
            if (showPassword.isSelected()) {
                lbl_passwordShowed.setText(newValue); // Cập nhật label với giá trị mới
            }
        });
    }


}