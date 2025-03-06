package org.example.phanmemthitracnghiem;

import BUS.UserBUS;
import DTO.UserDTO;
import Interface.UserAwareController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;

import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class User_Guest_Controller implements UserAwareController {

    @FXML
    private Label txtEmail;

    @FXML
    private PasswordField txtconfirmPass;

    @FXML
    private PasswordField txtnewPass;

    @FXML
    private PasswordField txtoldPass;

    @FXML
    private Label txtuserName;

    @FXML
    private Button updateBtn;

    private String username;

    @Override
    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public String getUserName() {
        return username;
    }

    public void loadUserData(UserDTO user) {
        if (user != null) {
            txtuserName.setText(user.getUserFullName());
            txtEmail.setText(user.getUserEmail());
        }
    }

    @FXML
    public void initialize() {
        // Gán sự kiện cho nút cập nhật mật khẩu
        updateBtn.setOnMouseClicked(this::updatePassword);
    }

    @FXML
    private void updatePassword(MouseEvent event) {
        String oldPass = txtoldPass.getText();
        String newPass = txtnewPass.getText();
        String confirmPass = txtconfirmPass.getText();

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            LoginController.showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            LoginController.showAlert("Lỗi", "Mật khẩu xác nhận không khớp.");
            return;
        }

        UserBUS userBUS = UserBUS.getInstance();
        boolean updateResult = userBUS.changePassword(username, oldPass, newPass);

        if (updateResult) {
            LoginController.showAlert("Thành công", "Mật khẩu đã được cập nhật.");

            // Xóa thông tin nhập vào
            txtoldPass.clear();
            txtnewPass.clear();
            txtconfirmPass.clear();

        } else {
            LoginController.showAlert("Lỗi", "Cập nhật mật khẩu thất bại. Kiểm tra lại mật khẩu cũ.");
        }
    }


}
