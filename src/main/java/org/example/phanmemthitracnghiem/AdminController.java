package org.example.phanmemthitracnghiem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AdminController {

    @FXML
    private BorderPane borderpane;

    public void setCenterContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = loader.load();
            borderpane.setCenter(newContent); // Thay đổi phần center của BorderPane
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        try {
            FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("Sidebar_Admin.fxml"));
            Node sidebar = sidebarLoader.load();
            Sidebar_Admin_Controller sidebarAdminController = sidebarLoader.getController();

            // Truyền AdminController vào SidebarController
            sidebarAdminController.setAdminController(this);

            borderpane.setLeft(sidebar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
