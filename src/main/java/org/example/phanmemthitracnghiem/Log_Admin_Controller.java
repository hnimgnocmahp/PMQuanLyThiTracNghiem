package org.example.phanmemthitracnghiem;

import BUS.LogBUS;
import BUS.TestCodeBUS;
import BUS.UserBUS;
import DTO.LogDTO;
import DTO.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Log_Admin_Controller {

    @FXML
    private TableView<LogDTO> tableView;

    @FXML
    private TableColumn<LogDTO, String> content;

    @FXML
    private TableColumn<LogDTO, LocalDateTime> date;

    @FXML
    private TableColumn<LogDTO, String> exCode;

    @FXML
    private ComboBox<String> exCodeField = new ComboBox<>();

    @FXML
    private TableColumn<LogDTO, Integer> id;

    @FXML
    private ComboBox<String> userIDField = new ComboBox<>();

    @FXML
    private TableColumn<LogDTO, Integer> userId;

    LogBUS logBUS = new LogBUS();

    private ObservableList<String> userIDList = FXCollections.observableArrayList();

    private ObservableList<String> exCodeList = FXCollections.observableArrayList();

    // Lưu trữ danh sách LogDTO gốc (không lọc)
    private List<LogDTO> allLogs;

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("logID"));
        content.setCellValueFactory(new PropertyValueFactory<>("logContent"));
        userId.setCellValueFactory(new PropertyValueFactory<>("logUserID"));
        exCode.setCellValueFactory(new PropertyValueFactory<>("logExCode"));
        date.setCellValueFactory(new PropertyValueFactory<>("logDate"));

        loadLog();
        loadIDUser();
        loadExCode();

        userIDField.setItems(userIDList);
        exCodeField.setItems(exCodeList);

        // Đăng ký sự kiện lọc khi giá trị ComboBox thay đổi
        userIDField.setOnAction(e -> filterLogs());
        exCodeField.setOnAction(e -> filterLogs());
    }

    private void loadLog() {
        // Lấy danh sách log từ BUS
        allLogs = new ArrayList<>();
        for (LogDTO log : logBUS.getAllLogs()) {
            allLogs.add(log);
        }
        // Hiển thị ban đầu tất cả log
        ObservableList<LogDTO> observableList = FXCollections.observableArrayList(allLogs);
        tableView.setItems(observableList);
    }

    private void loadIDUser() {
        List<String> tempList = new ArrayList<>();
        if (!tempList.contains("All")) {
            tempList.add("All");
        }
        for (UserDTO user : UserBUS.getInstance().getAllUsers()) {
            tempList.add(String.valueOf(user.getUserID()));
        }
        userIDList.setAll(tempList);
    }

    private void loadExCode() {
        List<String> tempList = new ArrayList<>();
        if (!tempList.contains("All")) {
            tempList.add("All");
        }
        for (String testCode : TestCodeBUS.getInstance().getAllExCode()) {
            tempList.add(testCode);
            System.out.println(testCode);
        }
        exCodeList.setAll(tempList);
    }

    // Phương thức lọc danh sách log dựa trên giá trị chọn trong ComboBox
    private void filterLogs() {
        String selectedUserID = userIDField.getValue();
        String selectedExCode = exCodeField.getValue();

        // Lọc danh sách theo userID và exCode
        List<LogDTO> filteredLogs = allLogs.stream().filter(log -> {
            boolean matchesUser = selectedUserID == null || selectedUserID.equals("All")
                    || String.valueOf(log.getLogUserID()).equals(selectedUserID);
            boolean matchesEx = selectedExCode == null || selectedExCode.equals("All")
                    || log.getLogExCode().equals(selectedExCode);
            return matchesUser && matchesEx;
        }).collect(Collectors.toList());

        ObservableList<LogDTO> observableList = FXCollections.observableArrayList(filteredLogs);
        tableView.setItems(observableList);
    }
}
