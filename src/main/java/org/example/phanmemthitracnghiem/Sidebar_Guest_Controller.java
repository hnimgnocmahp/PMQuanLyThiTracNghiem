package org.example.phanmemthitracnghiem;

import Interface.UserAwareController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Sidebar_Guest_Controller implements UserAwareController {
    private GuestController guestController;

    @FXML
    private VBox sidebar;

    @FXML
    private HBox statisticsButton;

    @FXML
    private HBox testButton1;

    @FXML
    private HBox userButton;

    private String username;

    @Override
    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public String getUserName() {
        return username;
    }


    @FXML
    void logout(MouseEvent event) {
        try {
            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tải lại Login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            // Tạo Scene mới
            Scene scene = new Scene(root, 600, 400);

            // Cập nhật Stage hiện tại
            stage.setTitle("Đăng nhập");
            stage.setScene(scene);
            // Căn giữa màn hình
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void setGuestController(GuestController guestController) {
        this.guestController = guestController;
    }

    @FXML
    public void initialize() {
        testButton1.setOnMouseClicked(event -> guestController.setCenterContent("Test_Guest.fxml", username));
        userButton.setOnMouseClicked(event -> guestController.setCenterContent("User_Guest.fxml", username));
        statisticsButton.setOnMouseClicked(event -> guestController.setCenterContent("Statistics_Guest.fxml", username));
    }

}
