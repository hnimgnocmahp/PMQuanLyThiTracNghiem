package org.example.phanmemthitracnghiem;

import BUS.UserBUS;
import Interface.UserAwareController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class GuestController implements UserAwareController {

    @FXML
    private BorderPane borderpane;
    private String username;
    // Lưu đối tượng Sidebar_Guest_Controller để cập nhật sau này
    private Sidebar_Guest_Controller sidebarGuestController;

    @Override
    public void setUserName(String username) {
        this.username = username;
        // Nếu sidebar đã được load, cập nhật luôn username cho nó
        if (sidebarGuestController != null) {
            sidebarGuestController.setUserName(username);
        }
    }



    @Override
    public String getUserName() {
        return username;
    }

    public void setCenterContent(String fxmlFile, String username1) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = loader.load();

            Object controller = loader.getController();

            if (controller instanceof UserAwareController) {
                ((UserAwareController) controller).setUserName(username1);
            }

            if (controller instanceof User_Guest_Controller){
                ((User_Guest_Controller) controller).loadUserData(UserBUS.getInstance().findUserByUserName(username1));
            }

            if (controller instanceof Exam_Controller){
                Exam_Controller examController = (Exam_Controller) controller;
                examController.setUserName(username1);
            }

            borderpane.setCenter(newContent); // Thay đổi phần center của BorderPane
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        try {
            FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("Sidebar_Guest.fxml"));
            Node sidebar = sidebarLoader.load();
            // Lưu lại controller của Sidebar để sử dụng sau
            sidebarGuestController = sidebarLoader.getController();

            // Truyền đối tượng GuestController vào Sidebar để có thể gọi các phương thức chuyển đổi content
            sidebarGuestController.setGuestController(this);
            // Nếu username đã được set trước đó, cập nhật cho sidebar
            if (username != null) {
                sidebarGuestController.setUserName(username);
            }
            borderpane.setLeft(sidebar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
