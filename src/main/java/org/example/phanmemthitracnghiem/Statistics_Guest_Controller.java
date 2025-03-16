package org.example.phanmemthitracnghiem;

import BUS.ResultBUS;
import BUS.TestBUS;
import BUS.UserBUS;
import DTO.ResultDTO;
import DTO.Statistics;
import DTO.TestDTO;
import DTO.TopicDTO;
import Session.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Statistics_Guest_Controller {


    @FXML
    private TableView<Statistics> tableView;
    @FXML
    private TableColumn<Statistics, String> exCode;

    @FXML
    private TableColumn<Statistics, String> rsAnswers;

    @FXML
    private TableColumn<Statistics, LocalDate> rsDate;

    @FXML
    private TableColumn<Statistics, BigDecimal> rsMark;

    @FXML
    private TableColumn<Statistics, Integer> rsNum;

    @FXML
    private TableColumn<Statistics, String> testTitle;

    @FXML
    public void initialize(){
        testTitle.setCellValueFactory(new PropertyValueFactory<>("testTitle"));
        exCode.setCellValueFactory(new PropertyValueFactory<>("exCode"));
        rsAnswers.setCellValueFactory(new PropertyValueFactory<>("rs_answer"));
        rsMark.setCellValueFactory(new PropertyValueFactory<>("rs_mark"));
        rsDate.setCellValueFactory(new PropertyValueFactory<>("rs_date"));
        rsNum.setCellValueFactory(new PropertyValueFactory<>("rs_num"));

        loadResult();
    }
    void loadResult(){
    ResultBUS resultBUS = ResultBUS.getInstance();
    TestBUS testBUS = TestBUS.getInstance();

    // Lấy userID từ Session (giả sử Session.username đã được thiết lập)
    int userID = UserBUS.getInstance().findUserByUserName(Session.username).getUserID();

    // Lấy danh sách kết quả của người dùng (ResultDTO)
    List<ResultDTO> resultList = resultBUS.getResultOfUser(userID);

    // Tạo danh sách hiển thị kết quả (ResultDisplay)
    List<Statistics> displayList = new ArrayList<>();
    for (ResultDTO resultDTO : resultList) {
        // Lấy TestDTO dựa vào exCode (giả sử testBUS có phương thức getTestByCode)
        TestDTO testDTO = testBUS.selectTestByTestCode(resultDTO.getExCode().substring(0, resultDTO.getExCode().length() - 1));
        String testTitleStr = (testDTO != null) ? testDTO.getTestTitle() : "N/A";
        displayList.add(new Statistics(resultDTO, testTitleStr));
    }

    // Chuyển danh sách thành ObservableList và gán cho TableView
    ObservableList<Statistics> observableList = FXCollections.observableArrayList(displayList);
    tableView.setItems(observableList);
}

}
