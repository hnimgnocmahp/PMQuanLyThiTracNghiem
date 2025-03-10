package org.example.phanmemthitracnghiem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Sidebar_Admin_Controller {
    private AdminController adminController;

    @FXML
    private HBox questionButton;

    @FXML
    private VBox sidebar;

    @FXML
    private HBox topicButton;

    @FXML
    private HBox statisticsButton;

    @FXML
    private HBox testButton;

    @FXML
    private HBox userButton;

    @FXML
    private HBox logButton;

    @FXML
    private HBox teststructureButton;

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

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    @FXML
    public void initialize() {
        // Thêm sự kiện nhấn vào từng HBox
        questionButton.setOnMouseClicked(event -> adminController.setCenterContent("Question_Admin.fxml"));
        testButton.setOnMouseClicked(event -> adminController.setCenterContent("Test_Admin.fxml"));
        userButton.setOnMouseClicked(event -> adminController.setCenterContent("User_Admin.fxml"));
        statisticsButton.setOnMouseClicked(event -> adminController.setCenterContent("Statistics_Admin.fxml"));
        topicButton.setOnMouseClicked(event -> adminController.setCenterContent("Topic_Admin.fxml"));
        logButton.setOnMouseClicked(event -> adminController.setCenterContent("Log_Admin.fxml"));
        teststructureButton.setOnMouseClicked(event -> adminController.setCenterContent("TestStructure_Admin.fxml"));
    }

}
