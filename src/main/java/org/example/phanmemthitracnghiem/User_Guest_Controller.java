package org.example.phanmemthitracnghiem;

import BUS.UserBUS;
import DTO.LogDTO;
import DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;


public class User_Guest_Controller {

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private PasswordField txtconfirmPass;

    @FXML
    private PasswordField txtnewPass;

    @FXML
    private PasswordField txtoldPass;

    @FXML
    private TextField txtuserName;

    @FXML
    private Button updateBtn;

    private static UserDTO loggedInUser;

    @FXML
    public void initialize() {
        // Kiểm tra nếu có người dùng đăng nhập
        if (loggedInUser != null) {
            txtId.setText(String.valueOf(loggedInUser.getUserID()));
            txtEmail.setText(loggedInUser.getUserEmail());
            txtuserName.setText(loggedInUser.getUserName());

            // Không cho chỉnh sửa các trường thông tin
            txtId.setEditable(false);
            txtEmail.setEditable(false);
            txtuserName.setEditable(false);
        }

        // Lắng nghe sự kiện chuột trên nút cập nhật mật khẩu
        updateBtn.setOnMouseClicked(event -> updatePassword(event));
    }

    private void updatePassword(MouseEvent event) {
        String oldPass = txtoldPass.getText();
        String newPass = txtnewPass.getText();
        String confirmPass = txtconfirmPass.getText();

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showAlert("Lỗi", "Mật khẩu xác nhận không khớp.");
            return;
        }

        UserBUS userBUS = UserBUS.getInstance();
        boolean updateResult = userBUS.changePassword(loggedInUser.getUserID(), oldPass, newPass);

        if (updateResult) {
            showAlert("Thành công", "Mật khẩu đã được cập nhật.");
            txtoldPass.clear();
            txtnewPass.clear();
            txtconfirmPass.clear();
        } else {
            showAlert("Lỗi", "Mật khẩu cũ không đúng hoặc có lỗi xảy ra.");
        }
    }

    private void showAlert(String title, String message) {
        LoginController.showAlert(title, message);
    }
}
