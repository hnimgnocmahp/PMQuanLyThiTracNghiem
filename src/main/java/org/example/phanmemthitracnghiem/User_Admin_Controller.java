package org.example.phanmemthitracnghiem;


import BUS.UserBUS;
import DTO.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class User_Admin_Controller {
    @FXML
    private TableColumn<UserDTO, Integer> columnUserID;

    @FXML
    private TableColumn<UserDTO, String> columnUserEmail;

    @FXML
    private TableColumn<UserDTO, String> columnUserFullName;

    @FXML
    private TableColumn<UserDTO, String> columnUserName;

    @FXML
    private TableView<UserDTO> tableUser;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtUserName;

    @FXML
    void addUser(MouseEvent event) {

    }

    @FXML
    void cancelAction(MouseEvent event) {

    }

    @FXML
    void deleteUser(MouseEvent event) {

    }

    @FXML
    void updateUser(MouseEvent event) {

    }

    public void loadUserData() {
        ObservableList<UserDTO> observableList = FXCollections.observableArrayList(UserBUS.getInstance().getAllUsers());
        tableUser.setItems(observableList);
    }

    @FXML
    public void initialize() {
        // Thiết lập giá trị cột dựa trên thuộc tính của UserDTO
        columnUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        columnUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        columnUserEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        columnUserFullName.setCellValueFactory(new PropertyValueFactory<>("userFullName"));

        // Tải dữ liệu từ CSDL vào bảng
        loadUserData();
    }
}
