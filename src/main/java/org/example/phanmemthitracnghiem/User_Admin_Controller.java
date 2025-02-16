package org.example.phanmemthitracnghiem;


import BUS.UserBUS;
import DTO.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
    private TextField txtSearch;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;


    @FXML
    private TextField txtUserName;

    @FXML
    private Label regex_email_user_admin;

    @FXML
    private Label regex_fullName_user_admin;

    @FXML
    private Label regex_userName_user_admin;


    @FXML
    void addUser(MouseEvent event) {
        String fullName = txtFullName.getText().trim();
        String userName = txtUserName.getText().trim();
        String email = txtEmail.getText().trim();

        boolean flag = false;

        if (fullName.isEmpty()) {
            flag = true;
            regex_fullName_user_admin.setText("Enter Full Name");
        } else {
            regex_fullName_user_admin.setText(null);
        }
        if (userName.isEmpty()) {
            flag = true;
            regex_userName_user_admin.setText("Enter User Name");
        } else {
            regex_userName_user_admin.setText(null);
        }
        if (email.isEmpty()) {
            flag = true;
            regex_email_user_admin.setText("Enter Email");
        } else {
            regex_email_user_admin.setText(null);
        }

        if (flag) {
            return;
        }

        UserDTO userDTO = new UserDTO(userName, email, "123", fullName, 1);
        int i = UserBUS.getInstance().addUser(userDTO);

        if (i > 0) {
            LoginController.showAlert("Thông báo", "Thêm thành công");
            loadUserData();
            resetText();
        } else if (i == -1){
            LoginController.showAlert("Thông báo", "User Name đã tồn tại");
        } else if (i == -2) {
            LoginController.showAlert("Thông báo", "Email đã tồn tại");
        }
    }

    @FXML
    void cancelAction(MouseEvent event) {
        resetText();
        loadUserData();
    }

    @FXML
    void deleteUser(MouseEvent event) {

    }

    @FXML
    void updateUser(MouseEvent event) {

    }

    @FXML
    void searchUser(KeyEvent event) {
        ObservableList<UserDTO> observableList = FXCollections.observableArrayList(UserBUS.getInstance().searchUsers(txtSearch.getText()));
        tableUser.setItems(observableList);
    }

    @FXML
    void getRowData(MouseEvent event) {
        UserDTO userDTO = tableUser.getSelectionModel().getSelectedItem();
        if (userDTO != null) {
            txtEmail.setText(userDTO.getUserEmail());
            txtFullName.setText(userDTO.getUserFullName());
            txtUserName.setText(userDTO.getUserName());
        }
    }

    public void resetText () {
        txtSearch.setText("");
        txtEmail.setText("");
        txtFullName.setText("");
        txtUserName.setText("");
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
