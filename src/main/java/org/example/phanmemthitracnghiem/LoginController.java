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
    private TextField fullName;

    @FXML
    private TextField userName_register;

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
        fullName.setVisible(true);
        userName_register.setVisible(true);
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
        fullName.setVisible(false);
        userName_register.setVisible(false);
        signin.setVisible(false);
        login_register.setVisible(false);
    }
    // Hàm truy cập trang đăng nhập
    @FXML
    void toLogPage(MouseEvent event) {
        toLogPagePublic();
    }


    // Hàm thông báo
    public static void showAlert(String title, String message) {
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
            UserDTO userDTO = UserBUS.getInstance().findUserByUserName(email);

            if (userDTO != null && userDTO.getUserName().equals(email) && userDTO.getUserPassword().equals(password) &&userDTO.getIsAdmin()==0) {
                switchToAdmin(event); // Truyền event vào switchToAdmin
            }else if (userDTO != null && userDTO.getUserName().equals(email) && userDTO.getUserPassword().equals(UserBUS.hashMD5(password)) &&userDTO.getIsAdmin()==1) {
                switchToGuest(event, email);
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
        UserDTO userDTO = UserBUS.getInstance().findUserByUserName(email);

        if (userDTO != null && userDTO.getUserName().equals(email) && userDTO.getUserPassword().equals(password) && userDTO.getIsAdmin()==0) {
            switchToAdmin(event); // Truyền event vào switchToAdmin
        }else if (userDTO != null && userDTO.getUserName().equals(email) && userDTO.getUserPassword().equals(UserBUS.hashMD5(password)) && userDTO.getIsAdmin()==1) {
            switchToGuest(event, email);
        } else {
            showAlert("Lỗi", "Tài khoản hoặc mật khẩu không đúng!");
        }
    }

    // Hàm đăng kí
    @FXML
    void register(MouseEvent event) {
        String fn = fullName.getText();
        String un = userName_register.getText();
        String email = email_register.getText();
        String password = password_register.getText();
        String confirmPassword = confirmPassword_register.getText();

        if (password.equals(confirmPassword)) {
            UserDTO userDTO = new UserDTO(un, email, password, fn, 1);
            int i = UserBUS.getInstance().addUser(userDTO);
            if (i > 0) {
                toLogPagePublic();
                fullName.setText(null);
                userName_register.setText(null);
                email_register.setText(null);
                password_register.setText(null);
                confirmPassword_register.setText(null);
                LoginController.showAlert("Thông báo", "Đăng ký thành công");
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
    private void switchToGuest(KeyEvent event, String username) {
        try {
            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tải Admin.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Guest.fxml"));
            Parent root = loader.load();

            GuestController guestController = loader.getController();


            // Tạo Scene mới
            Scene scene = new Scene(root);
            guestController.setUserName(username);

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
    private void switchToGuest(MouseEvent event, String username) {
        try {
            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tải Admin.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Guest.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            GuestController guestController = loader.getController();
            guestController.setUserName(username);
//            System.out.println(username);

            // Tạo Scene mới
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
        userName_register.setVisible(false);
        fullName.setVisible(false);
        signin.setVisible(false);
        login_register.setVisible(false);

        password_login.textProperty().addListener((observable, oldValue, newValue) -> {
            if (showPassword.isSelected()) {
                lbl_passwordShowed.setText(newValue); // Cập nhật label với giá trị mới
            }
        });
    }


}