package org.example.phanmemthitracnghiem;

import BUS.*;
import DTO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class TestStructure_Admin_Controller {
    @FXML
    private TableColumn<TestStructureDTO, Integer> columnNumDiff;

    @FXML
    private TableColumn<TestStructureDTO, Integer> columnNumEasy;

    @FXML
    private TableColumn<TestStructureDTO, Integer> columnNumMedium;

    @FXML
    private TableColumn<TestStructureDTO, String> columnTestCode;

    @FXML
    private TableColumn<TestStructureDTO, Integer> columnTpID;

    @FXML
    private TableView<TestStructureDTO> tableTestStructure;

    @FXML
    private TextField txtEasy;

    @FXML
    private TextField txtHard;

    @FXML
    private TextField txtMedium;

    @FXML
    private TextField txtNumTestCode;

    @FXML
    private ComboBox<String> txtTopic;

    @FXML
    private ComboBox<String> txtTestCode;

    @FXML
    private ComboBox<String> txtTestCode_Exam;

    @FXML
    void addTestStructure(MouseEvent event) {
        String testCode = txtTestCode.getValue();
        String topic = txtTopic.getSelectionModel().getSelectedItem();
        int spaceIndex = topic.indexOf(" ");
        String result = (spaceIndex != -1) ? topic.substring(0, spaceIndex) : topic;
        System.out.println(result);
        int num_easy = Integer.parseInt(txtEasy.getText());
        int num_medium = Integer.parseInt(txtMedium.getText());
        int num_hard = Integer.parseInt(txtHard.getText());
        TestStructureDTO testStructureDTO = new TestStructureDTO();
        testStructureDTO.setTestCode(testCode);
        testStructureDTO.setTpID(TopicBUS.getInstance().getTopicByTitle(result).getTopicID());
        testStructureDTO.setNum_easy(num_easy);
        testStructureDTO.setNum_medium(num_medium);
        testStructureDTO.setNum_diff(num_hard);
        testStructureDTO.setTestCode(testCode);

        TestStructureBUS.getInstance().addTestStructure(testStructureDTO);

        loadTestStructureData();
    }

    @FXML
    void cancel(MouseEvent event) {

    }

    @FXML
    void createExam(MouseEvent event) {
        String testCode = txtTestCode_Exam.getSelectionModel().getSelectedItem();
        int numTestCode = Integer.parseInt(txtNumTestCode.getText());
        //Lấy danh sách tất cả chủ đề của testcode
        ArrayList<TopicDTO> topicList = new ArrayList<>();
        List<TestStructureDTO> testStructureList = TestStructureBUS.getInstance().getStructureByTestCode(testCode);
        for (TestStructureDTO testStructureDTO : testStructureList) {
            topicList.addAll(TopicBUS.getInstance().getTopicsChildByID(testStructureDTO.getTpID()));

        }


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

            for (TestStructureDTO testStructureDTO : testStructureList) {
                if (QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "easy", testStructureDTO.getNum_easy()).size() < testStructureDTO.getNum_easy()) {
                    LoginController.showAlert("Thông báo", "Không đủ câu hỏi dễ");
                    return;
                } else {
                    //Chọn ngẫu nhiên câu hỏi dễ trong các chủ đề
                    selectedQuestions.addAll(QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "easy", testStructureDTO.getNum_easy()));
                }

                if (QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "medium", testStructureDTO.getNum_medium()).size() < testStructureDTO.getNum_medium()) {
                    LoginController.showAlert("Thông báo", "Không đủ câu hỏi trung bình");
                    return;
                } else {
                    //Chọn ngẫu nhiên câu hỏi trung bình trong các chủ đề
                    selectedQuestions.addAll(QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "medium", testStructureDTO.getNum_medium()));
                }

                if (QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "hard", testStructureDTO.getNum_diff()).size() < testStructureDTO.getNum_diff()) {
                    LoginController.showAlert("Thông báo", "Không đủ câu khó");
                    return;
                } else {
                    //Chọn ngẫu nhiên câu hỏi khó trong các chủ đề
                    selectedQuestions.addAll(QuestionBUS.getInstance().selectRandomQuestions(allQuestions, "hard", testStructureDTO.getNum_diff()));;
                }
            }

            // Chuyển đổi danh sách câu hỏi thành chuỗi text
            String exQuestionIDsText = convertQuestionListToText(selectedQuestions);

            //Thêm Testcode
            TestCodeDTO exams = new TestCodeDTO(testCode, str,testCode + str, exQuestionIDsText);
            TestCodeBUS.getInstance().add(exams);
            char ch = str.charAt(0);
            ch = (char) (ch + 1);
            str = String.valueOf(ch);
        }
    }

    @FXML
    void deleteTestStructure(MouseEvent event) {

    }

    @FXML
    public void initialize() {
        columnTestCode.setCellValueFactory(new PropertyValueFactory<>("testCode"));
        columnTpID.setCellValueFactory(new PropertyValueFactory<>("tpID"));
        columnNumEasy.setCellValueFactory(new PropertyValueFactory<>("num_easy"));
        columnNumMedium.setCellValueFactory(new PropertyValueFactory<>("num_medium"));
        columnNumDiff.setCellValueFactory(new PropertyValueFactory<>("num_diff"));

        getTopicForCombobox();
        loadTestStructureData();

        getTestCodeComboboxWithNewTest();


    }

    // Hàm lấy testCode cho combobox
    private void getTestCodeComboboxWithNewTest() {
        List<TestDTO> testList = TestBUS.getInstance().getTests();
        List<String> testCodeList = new ArrayList<>();
        for (TestDTO testDTO : testList) {
            testCodeList.add(testDTO.getTestCode());
        }
        txtTestCode.setItems(FXCollections.observableList(testCodeList));
        txtTestCode_Exam.setItems(FXCollections.observableList(testCodeList));
    }

    // Hàm lấy các môn trong một chủ đề và qua tất cả chủ đề
    private void getTopicForCombobox() {
        List<TopicDTO> topicListNull = TopicBUS.getInstance().getTopicWithParentNull();
        String topicListTitle = "";
        List<TopicDTO> topicListWithChildren = new ArrayList<>();

        for (TopicDTO topic : topicListNull) {
            topicListTitle = "";
            topicListWithChildren = TopicBUS.getInstance().getTopicsChildByID(topic.getTopicID());

            for (TopicDTO topicChild : topicListWithChildren) {
                topicListTitle += topicChild.getTopicTitle() + " ";
            }

            if (topicListTitle != "") {
                txtTopic.getItems().add(topicListTitle);
            }

        }
    }

    // Lấy tất cả dữ liệu bảng TestStructure
    public void loadTestStructureData() {
        ObservableList<TestStructureDTO> observableList = FXCollections.observableArrayList(TestStructureBUS.getInstance().getAllTestStructure());
        tableTestStructure.setItems(observableList);
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
