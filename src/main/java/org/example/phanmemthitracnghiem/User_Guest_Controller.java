package org.example.phanmemthitracnghiem;

import BUS.UserBUS;
import DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class User_Guest_Controller {

    @FXML
    private TextField txtEmail;

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

    private UserDTO currentUser;

    private void reloadUserGuestPage() {
        try {
            // Lấy Stage hiện tại
            Stage stage = (Stage) txtoldPass.getScene().getWindow();

            // Load lại giao diện User_Guest.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("User_Guest.fxml"));
            Parent root = loader.load();

            // Truyền thông tin user vào Controller mới
            User_Guest_Controller controller = loader.getController();
            controller.setCurrentUser(currentUser.getUserName());

            // Cập nhật Scene và hiển thị lại
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            LoginController.showAlert("Lỗi", "Không thể tải lại giao diện!");
        }
    }

    public void setCurrentUser(String userName) {
        this.currentUser = UserBUS.getInstance().findUserByUserName(userName);
        if (this.currentUser != null) {
            loadUserData(this.currentUser);
        }
    }

    private void loadUserData(UserDTO user) {
        if (user != null) {
            txtuserName.setText(user.getUserName());
            txtEmail.setText(user.getUserEmail());
        }
    }

    @FXML
    public void initialize() {
        // Gán sự kiện cho nút cập nhật mật khẩu
        updateBtn.setOnMouseClicked(this::updatePassword);
        txtuserName.setText();
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
        boolean updateResult = userBUS.changePassword(txtuserName.getText(), oldPass, newPass);

        if (updateResult) {
            LoginController.showAlert("Thành công", "Mật khẩu đã được cập nhật.");

            // Xóa thông tin nhập vào
            txtoldPass.clear();
            txtnewPass.clear();
            txtconfirmPass.clear();

            // Load lại trang để cập nhật dữ liệu mới
            reloadUserGuestPage();
        } else {
            LoginController.showAlert("Lỗi", "Cập nhật mật khẩu thất bại. Kiểm tra lại mật khẩu cũ.");
        }
    }
}
