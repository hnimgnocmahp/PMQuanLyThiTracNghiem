package org.example.phanmemthitracnghiem;


import BUS.*;
import DTO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.poi.hssf.record.FontRecord;
import org.apache.poi.ss.formula.functions.Log;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Test_Admin_Controller {

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<TestDTO, LocalDateTime> columnDate;

    @FXML
    private TableColumn<TestDTO, Integer> columnLimit;

    @FXML
    private TableColumn<TestDTO, String> columnTestCode;

    @FXML
    private TableColumn<TestDTO, Integer> columnTestID;

    @FXML
    private TableColumn<TestDTO, Integer> columnStatus;

    @FXML
    private TableColumn<TestDTO, Integer> columnTime;

    @FXML
    private TableColumn<TestDTO, String> columnTitle;

    @FXML
    private TableView<TestDTO> tableTest;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtLimit;

    @FXML
    private TextField txtTestCode;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtTitle;

    @FXML
    void addTest(MouseEvent event) {
        String code = txtTestCode.getText();
        String title = txtTitle.getText();
        int time = Integer.parseInt(txtTime.getText());

        int limit = Integer.parseInt(txtLimit.getText());
        Date date = Date.valueOf(txtDate.getValue());
        int status = 1;
//


        TestDTO test = new TestDTO(code, title, time ,limit, date, status);

        int addTest = TestBUS.getInstance().addTest(test);
//
        if (addTest > 0) {
            LoginController.showAlert("Thông báo", "Thêm test thành công");
            loadTestData();
        } else {
            LoginController.showAlert("Thông báo", "Thêm test thất bại");
        }
    }


    @FXML
    void searchTestbyClick(MouseEvent event) {
        List<TestDTO> list = new ArrayList<>();
        String key = searchField.getText();
        for (TestDTO test : TestBUS.getInstance().getTests()) {
            if (test.getTestTitle().toUpperCase().contains(key.toUpperCase())) {
                list.add(test);
            }
        }
        ObservableList<TestDTO> observableList = FXCollections.observableArrayList(list);
        tableTest.setItems(observableList);
    }

    @FXML
    void cancel(MouseEvent event) {

    }

    @FXML
    void deleteTest(MouseEvent event) {
        TestDTO testDTO = tableTest.getSelectionModel().getSelectedItem();
        if (testDTO != null) {
            TestBUS.getInstance().updateTest(testDTO);
            loadTestData();
        } else {
            LoginController.showAlert("Thông báo", "Chưa có mục nào được chọn!");
        }
    }

    @FXML
    void searchTest(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            List<TestDTO> list = new ArrayList<>();
            if (event.getCode() == KeyCode.ENTER) {
                String key = searchField.getText();
                for (TestDTO test : TestBUS.getInstance().getTests()){
                    if (test.getTestTitle().toUpperCase().contains(key.toUpperCase())){
                        list.add(test);
                    }
                }
            }
            ObservableList<TestDTO> observableList = FXCollections.observableArrayList(list);
            tableTest.setItems(observableList);
        }
    }

    @FXML
    void updateTest(MouseEvent event) {

    }

    @FXML
    public void initialize() {
        columnTestID.setCellValueFactory(new PropertyValueFactory<>("testID"));
        columnTestCode.setCellValueFactory(new PropertyValueFactory<>("testCode"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("testTitle"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("testTime"));
        columnLimit.setCellValueFactory(new PropertyValueFactory<>("testLimit"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("testDate"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("testStatus"));

//        getTopicForCombobox();
        loadTestData();


    }

    // Lấy tất cả dữ liệu bảng Test
    public void loadTestData() {
        ObservableList<TestDTO> observableList = FXCollections.observableArrayList(TestBUS.getInstance().getTests());
        tableTest.setItems(observableList);
    }

    public String convertQuestionListToText(List<QuestionDTO> questions) {
        StringBuilder sb = new StringBuilder();
        for (QuestionDTO question : questions) {
            sb.append(question.getQID()).append(","); // Giả sử `getQID()` trả về ID của câu hỏi
        }
        // Xóa dấu phẩy cuối cùng
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }




}
