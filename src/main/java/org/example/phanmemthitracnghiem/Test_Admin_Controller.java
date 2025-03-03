package org.example.phanmemthitracnghiem;


import BUS.*;
import DTO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<TestDTO, LocalDateTime> columnDate;

    @FXML
    private TableColumn<TestDTO, Integer> columnDiff;

    @FXML
    private TableColumn<TestDTO, Integer> columnEasy;

    @FXML
    private TableColumn<TestDTO, Integer> columnLimit;

    @FXML
    private TableColumn<TestDTO, Integer> columnMedium;

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
    private TableColumn<TestDTO, Integer> columnTopic;

    @FXML
    private TableView<TestDTO> tableTest;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtEasy;

    @FXML
    private TextField txtHard;

    @FXML
    private TextField txtLimit;

    @FXML
    private TextField txtMedium;

    @FXML
    private TextField txtTestCode;

    @FXML
    private TextField txtNumTestCode;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtTitle;

    @FXML
    private ComboBox<String> txtTopic;

    @FXML
    void addTest(MouseEvent event) {
        String code = txtTestCode.getText();
        String title = txtTitle.getText();
        int time = Integer.parseInt(txtTime.getText());
        TopicDTO topicDTO = TopicBUS.getInstance().getTopicByTitle(new String(txtTopic.getValue().split("=")[0]));
        int topicID = topicDTO.getTopicID();
        int ease = Integer.parseInt(txtEasy.getText());
        int medium = Integer.parseInt(txtMedium.getText());
        int diff = Integer.parseInt(txtHard.getText());
        int limit = Integer.parseInt(txtLimit.getText());
        Date date = Date.valueOf(txtDate.getValue());
        int status = 1;
        int numTestCode = Integer.parseInt(txtNumTestCode.getText());

        //Lấy danh sách chủ đề con
        ArrayList<TopicDTO> topicList = new ArrayList<>();
        topicList.addAll(TopicBUS.getInstance().getTopicsChildByID(topicID));

        //Danh sách câu hỏi trong các chủ đề con
        List<QuestionDTO> selectedQuestions;

        // Lấy tất cả câu hỏi từ các chủ đề con
        List<QuestionDTO> allQuestions = new ArrayList<>();
        for (TopicDTO topic : topicList) {
            allQuestions.addAll(QuestionBUS.getInstance().getQuestionsForTopic(topic.getTopicID()));
        }

        String str = "A";
        for (int i = 0; i < numTestCode; i++) {

            selectedQuestions = new ArrayList<>();

            if (QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "easy", ease).size() < ease) {
                LoginController.showAlert("Thông báo", "Không đủ câu hỏi dễ");
                return;
            } else {
                //Chọn ngẫu nhiên câu hỏi dễ trong các chủ đề
                selectedQuestions.addAll(QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "easy", ease));
            }

            if (QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "medium", medium).size() < medium) {
                LoginController.showAlert("Thông báo", "Không đủ câu hỏi trung bình");
                return;
            } else {
                //Chọn ngẫu nhiên câu hỏi trung bình trong các chủ đề
                selectedQuestions.addAll(QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "medium", medium));
            }

            if (QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "hard", diff).size() < diff) {
                LoginController.showAlert("Thông báo", "Không đủ câu khó");
                return;
            } else {
                //Chọn ngẫu nhiên câu hỏi khó trong các chủ đề
                selectedQuestions.addAll(QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "hard", diff));;
            }


            // Chuyển đổi danh sách câu hỏi thành chuỗi text
            String exQuestionIDsText = convertQuestionListToText(selectedQuestions);

            //Thêm Testcode
            TestCodeDTO testCode = new TestCodeDTO(code, str,code + str, exQuestionIDsText);
            TestCodeBUS.getInstance().add(testCode);
            char ch = str.charAt(0);
            ch = (char) (ch + 1);
            str = String.valueOf(ch);
        }


        TestDTO test = new TestDTO(code, title, time, topicID, ease, medium, diff,limit, date, status);

        int addTest = TestBUS.getInstance().addTest(test);

        if (addTest > 0) {
            LoginController.showAlert("Thông báo", "Thêm test thành công với " + numTestCode + " mã đề");
            loadTestData();
        } else {
            LoginController.showAlert("Thông báo", "Thêm test thất bại" + numTestCode + " mã đề");
        }
    }

    @FXML
    void cancel(MouseEvent event) {

    }

    @FXML
    void deleteTest(MouseEvent event) {

    }

    @FXML
    void searchTest(KeyEvent event) {

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
        columnTopic.setCellValueFactory(new PropertyValueFactory<>("tpID"));
        columnEasy.setCellValueFactory(new PropertyValueFactory<>("num_ease"));
        columnMedium.setCellValueFactory(new PropertyValueFactory<>("num_medium"));
        columnDiff.setCellValueFactory(new PropertyValueFactory<>("num_diff"));
        columnLimit.setCellValueFactory(new PropertyValueFactory<>("testLimit"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("testDate"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("testStatus"));

        getTopicForCombobox();
        loadTestData();


    }


    // Hàm lấy các môn trong một chủ đề và qua tất cả chủ đề
    private void getTopicForCombobox() {
        List<TopicDTO> topicListNull = TopicBUS.getInstance().getTopicWithParentNull();
        String topicListTitle = "";
        List<TopicDTO> topicListWithChildren = new ArrayList<>();

        for (TopicDTO topic : topicListNull) {
            topicListTitle = topic.getTopicTitle() + "=";
            topicListWithChildren = TopicBUS.getInstance().getTopicsChildByID(topic.getTopicID());

            for (TopicDTO topicChild : topicListWithChildren) {
                topicListTitle += topicChild.getTopicTitle() + "  ";
            }

            if (topicListTitle != "") {
                txtTopic.getItems().add(topicListTitle);
            }

        }
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
