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

    private UserDTO currentUser;

    private String username;

    @Override
    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public String getUserName() {
        return username;
    }

    private void reloadUserGuestPage() {
        try {

            // Lấy Stage hiện tại
            Stage stage = (Stage) txtoldPass.getScene().getWindow();

            // Load lại giao diện User_Guest.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("User_Guest.fxml"));
            Parent root = loader.load();

            // Truyền thông tin user vào Controller mới
            User_Guest_Controller controller = loader.getController();
            controller.setCurrentUser(username);


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

    public UserDTO setCurrentUser(String userName) {
        this.currentUser = UserBUS.getInstance().findUserByUserName(userName);
        if (this.currentUser != null) {
             loadUserData(this.currentUser);
        }
        return this.currentUser;
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
//        System.out.println(username);

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

            // Load lại trang để cập nhật dữ liệu mới
//            reloadUserGuestPage();
//            setCurrentUser(this.currentUser.getUserName());

        } else {
            LoginController.showAlert("Lỗi", "Cập nhật mật khẩu thất bại. Kiểm tra lại mật khẩu cũ.");
        }
    }


}
