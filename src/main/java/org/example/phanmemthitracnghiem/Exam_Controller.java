package org.example.phanmemthitracnghiem;

import BUS.*;
import DTO.*;
import Interface.UserAwareController;
import Session.Session;
import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exam_Controller  implements UserAwareController {

    private String username;

    private String exCodeDTO;


    @Override
    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @FXML
    private VBox answerContainer;

    @FXML
    private VBox questionContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox sidebar;

    @FXML
    private VBox sidebar1;

    @FXML
    private Label time;

    @FXML
    private Label exCode;

    private TopicBUS topicBUS;

    private QuestionBUS questionBUS;

    private int currentQuestionIndex = 0; // Chỉ số câu hỏi hiện tại

    private Map<Integer, String> userAnswers = new HashMap<>(); // Lưu đáp án người dùng chọn

    private List<QuestionDTO> questionsContent = new ArrayList<>(); // Danh sách câu hỏi

    private List<AnswerDTO[]> answerChoices = new ArrayList<>(); // Danh sách đáp án tương ứng

    private ToggleGroup answerGroup = new ToggleGroup();

    @FXML
    public void initialize() {
        topicBUS = new TopicBUS();

        questionBUS = new QuestionBUS();
        
        setupScrollPane();
    }

    public void initializeExam(String testCode) {
        TestDTO testDTO = TestBUS.getInstance().selectTestByTestCode(testCode);
        startCountdown(testDTO.getTestTime());
        loadQuestion(testCode);
        loadQuestionContent();
    }

    private void startCountdown(int totalMinutes) {
        // Sử dụng mảng để lưu trữ giá trị có thể thay đổi
        final int[] totalSeconds = { totalMinutes * 60 };

        // Hiển thị thời gian ban đầu ngay lập tức
        time.setText(String.format("%d : 00", totalMinutes));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Tính toán phút và giây còn lại
            int minutesLeft = totalSeconds[0] / 60;
            int secondsLeft = totalSeconds[0] % 60;

            // Định dạng hiển thị
            String timeDisplay = String.format("%d : %02d", minutesLeft, secondsLeft);
            time.setText(timeDisplay);

            // Giảm tổng số giây
            if (totalSeconds[0] > 0) {
                totalSeconds[0]--;
            } else {
                // Hết giờ - dừng timeline
                ((Timeline) event.getSource()).stop();
//                handleTimeOut();
            }
        }));

        // Thiết lập để chạy liên tục
        timeline.setCycleCount(totalMinutes * 60);
        timeline.play();
    }

    private void setupScrollPane() {
        // Gán sidebar1 vào ScrollPane và thiết lập cho phù hợp
        scrollPane.setContent(sidebar1);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    @FXML
    void next(MouseEvent event) {
        // Lưu đáp án hiện tại
        saveCurrentAnswer();

        // Nếu chưa phải câu cuối cùng
        if (currentQuestionIndex < questionsContent.size() - 1) {
            // Lấy đáp án của câu hiện tại (chưa thay đổi chỉ số)
            String answer = userAnswers.get(currentQuestionIndex);

            // Kiểm tra đáp án (đã chọn hay chưa)
            if (answer != null && !answer.trim().isEmpty()) {
                // Nếu đã chọn đáp án thì đánh dấu xanh
                sidebar1.getChildren().get(currentQuestionIndex)
                        .setStyle("-fx-background-color: #60f772");
            } else {
                // Nếu chưa chọn đáp án thì đánh dấu đỏ
                sidebar1.getChildren().get(currentQuestionIndex)
                        .setStyle("-fx-background-color: red");
            }

            // Tăng chỉ số câu hỏi và load câu hỏi mới
            currentQuestionIndex++;
            loadQuestionContent();
        }
    }

    @FXML
    void previous(MouseEvent event) {
        if (currentQuestionIndex > 0) {
            saveCurrentAnswer();
            // Kiểm tra đáp án (đã chọn hay chưa)
            String answer = userAnswers.get(currentQuestionIndex);
            if (answer != null && !answer.trim().isEmpty()) {
                // Nếu đã chọn đáp án thì đánh dấu xanh
                sidebar1.getChildren().get(currentQuestionIndex)
                        .setStyle("-fx-background-color: #60f772");
            } else {
                // Nếu chưa chọn đáp án thì đánh dấu đỏ
                sidebar1.getChildren().get(currentQuestionIndex)
                        .setStyle("-fx-background-color: red");
            }
            currentQuestionIndex--;
            loadQuestionContent();
        }
    }

    @FXML
    void submit(MouseEvent event) {
        // Lưu đáp án của câu hỏi hiện tại
        saveCurrentAnswer();

        int total = questionsContent.size();
        int correctCount = 0;
        List<QuestionResult> detailedResults = new ArrayList<>();

        // Duyệt qua từng câu hỏi để tính điểm và tạo kết quả chi tiết
        for (int i = 0; i < total; i++) {
            String userAns = userAnswers.get(i);
            if (userAns == null) {
                userAns = "Không chọn";
            }

            String correctAns = "";
            for (AnswerDTO answer : answerChoices.get(i)) {
                if (answer.getIsRight() == 1) { // Giả sử getIsRight() trả về 1 nếu đáp án đúng
                    correctAns = answer.getAwContent();
                    break;
                }
            }
            boolean isCorrect = userAns.equals(correctAns);
            if (isCorrect) {
                correctCount++;
            }
            String resultStr = isCorrect ? "Đúng" : "Sai";
            detailedResults.add(new QuestionResult(i + 1,
                    questionsContent.get(i).getQContent(),
                    userAns,
                    correctAns,
                    resultStr));
        }

        // Tính điểm (ví dụ điểm tối đa 10)
        BigDecimal mark = BigDecimal.valueOf(10.0 * correctCount / total);

        // Tạo TableView để hiển thị kết quả chi tiết từng câu hỏi
        TableView<QuestionResult> tableView = new TableView<>();

        TableColumn<QuestionResult, Integer> colQuestionNo = new TableColumn<>("Câu");
        colQuestionNo.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));

        TableColumn<QuestionResult, String> colQuestionContent = new TableColumn<>("Nội dung câu hỏi");
        colQuestionContent.setCellValueFactory(new PropertyValueFactory<>("questionContent"));

        TableColumn<QuestionResult, String> colUserAnswer = new TableColumn<>("Đáp án của bạn");
        colUserAnswer.setCellValueFactory(new PropertyValueFactory<>("userAnswer"));

        TableColumn<QuestionResult, String> colCorrectAnswer = new TableColumn<>("Đáp án đúng");
        colCorrectAnswer.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        TableColumn<QuestionResult, String> colResult = new TableColumn<>("Kết quả");
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));

        tableView.getColumns().addAll(colQuestionNo, colQuestionContent, colUserAnswer, colCorrectAnswer, colResult);
        tableView.getItems().addAll(detailedResults);

        // Tạo Label hiển thị điểm tổng
        Label scoreLabel = new Label("Điểm của bạn: " + mark + "(" + correctCount + " / " + total +")");


        // Tạo nút "Quay lại" để chuyển về giao diện Test_Guest_Controller
        Button backToTestListButton = new Button("Quay lại giao diện đề thi");
        backToTestListButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Guest.fxml"));
                Parent testGuestRoot = loader.load();
                Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                stage.setScene(new Scene(testGuestRoot, 944, 580));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Gộp các thành phần (Label, TableView, nút quay lại) vào VBox
        VBox vbox = new VBox(10, scoreLabel, tableView, backToTestListButton);
        vbox.setStyle("-fx-padding: 10;");

        // Thay thế Scene hiện tại bằng giao diện kết quả
        Stage currentStage = (Stage) questionContainer.getScene().getWindow();
        currentStage.setScene(new Scene(vbox, 800, 600));
        currentStage.show();



        // Lưu vào bảng result
//        UserDTO userDTO = UserBUS.getInstance().findUserByUserName(Session.username);
//        String jsonAnswers = new Gson().toJson(userAnswers.toString());
//        ResultDTO resultDTO = new ResultDTO(1, userDTO.getUserID(), exCodeDTO, jsonAnswers, mark, LocalDate.now());
//        ResultBUS.getInstance().addResult(resultDTO);



    }


    // Lớp nội bộ lưu trữ kết quả chi tiết của từng câu hỏi
    public static class QuestionResult {
        private int questionNumber;
        private String questionContent;
        private String userAnswer;
        private String correctAnswer;
        private String result;

        public QuestionResult(int questionNumber, String questionContent, String userAnswer, String correctAnswer, String result) {
            this.questionNumber = questionNumber;
            this.questionContent = questionContent;
            this.userAnswer = userAnswer;
            this.correctAnswer = correctAnswer;
            this.result = result;
        }

        public int getQuestionNumber() {
            return questionNumber;
        }

        public String getQuestionContent() {
            return questionContent;
        }

        public String getUserAnswer() {
            return userAnswer;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public String getResult() {
            return result;
        }
    }


    private void saveCurrentAnswer() {
        Toggle selectedToggle = answerGroup.getSelectedToggle();
        if (selectedToggle instanceof RadioButton) {
            RadioButton selectedRadio = (RadioButton) selectedToggle;
            userAnswers.put(currentQuestionIndex, selectedRadio.getText());
        }
    }

    public void loadQuestion(String testCode) {
        TestCodeDTO testCodeDTO = TestCodeBUS.getInstance().getRandomTestCode(testCode);

        exCodeDTO = testCodeDTO.getExCode();
        //set Mã đề lên view
        exCode.setText(testCodeDTO.getExCode());

        String ex_quesID = testCodeDTO.getEx_questionIDs().replaceAll("\\[|\\]|\\s", "");
        String[] strArray = ex_quesID.split(","); // Tách chuỗi thành mảng chuỗi

        QuestionDTO questionDTO = new QuestionDTO();

        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
            questionDTO = QuestionBUS.getInstance().getQuestionById(intArray[i]);
            List<AnswerDTO> listAnswer = new ArrayList<>();
            questionsContent.add(questionDTO);
            for (AnswerDTO answerDTO : AnswerBUS.getInstance().getAnswersByQuestionID(questionDTO.getQID())) {
                listAnswer.add(answerDTO);
            }
            answerChoices.add(listAnswer.toArray(new AnswerDTO[0]));
        }

        // Tạo sidebar hiển thị danh sách câu hỏi (ví dụ: số câu và trạng thái đã làm/chưa làm)
        for (int i = 0; i < questionsContent.size(); i++) {
            Label questionLabel = new Label("Câu " + (i + 1));
            questionLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: normal; -fx-font-family: 'Times New Roman'");
            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
            HBox questionBox = new HBox(20, questionLabel, spacer);
            questionBox.setAlignment(Pos.CENTER);
            sidebar1.getChildren().add(questionBox);
        }
    }

    public void loadQuestionContent() {
        if (questionsContent.isEmpty()) return;

        // Xóa nội dung cũ
        questionContainer.getChildren().clear();
        answerContainer.getChildren().clear();

        // Hiển thị nội dung câu hỏi
        QuestionDTO currentQuestion = questionsContent.get(currentQuestionIndex);
        Label questionLabel = new Label("câu " + (currentQuestionIndex + 1)  + ": " + currentQuestion.getQContent());
        questionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: normal; -fx-font-family: 'Times New Roman'");
        questionContainer.getChildren().add(questionLabel);

        // Nếu câu hỏi có ảnh, load ảnh từ classpath hoặc file system
        if (currentQuestion.getQPictures() != null && !currentQuestion.getQPictures().isEmpty()) {
            try {
                // Thử load từ classpath (resources)
                InputStream inputStream = getClass().getResourceAsStream("/" + currentQuestion.getQPictures());
                Image questionImage;
                if (inputStream == null) {
                    // Không tìm thấy trong classpath, chuyển sang file system
                    File file = new File(currentQuestion.getQPictures());
                    String imageUrl = file.toURI().toString();
                    questionImage = new Image(imageUrl);
                } else {
                    questionImage = new Image(inputStream);
                }
                ImageView questionImageView = new ImageView(questionImage);
                questionImageView.setFitWidth(100);
                questionImageView.setPreserveRatio(true);
                questionContainer.getChildren().add(questionImageView);
            } catch (Exception e) {
                System.err.println("Không thể load ảnh của câu hỏi: " + e.getMessage());
            }
        }

        // Hiển thị danh sách đáp án
        answerGroup = new ToggleGroup();  // Reset ToggleGroup mỗi lần load câu hỏi
        AnswerDTO[] answers = answerChoices.get(currentQuestionIndex);
        for (AnswerDTO answer : answers) {
            RadioButton radioButton = new RadioButton(answer.getAwContent());
            radioButton.setToggleGroup(answerGroup);
            // Nếu người dùng đã chọn đáp án trước đó, đánh dấu nó
            if (userAnswers.containsKey(currentQuestionIndex) &&
                    userAnswers.get(currentQuestionIndex).equals(answer.getAwContent())) {
                radioButton.setSelected(true);
            }

            // Kiểm tra nếu đáp án có ảnh
            if (answer.getAwPictures() != null && !answer.getAwPictures().isEmpty()) {
                try {
                    // Thử load ảnh của đáp án từ classpath
                    InputStream inputStream = getClass().getResourceAsStream("/" + answer.getAwPictures());
                    Image answerImage;
                    if (inputStream == null) {
                        // Nếu không tìm thấy trong classpath, chuyển sang file system
                        File file = new File(answer.getAwPictures());
                        String imageUrl = file.toURI().toString();
                        answerImage = new Image(imageUrl);
                    } else {
                        answerImage = new Image(inputStream);
                    }
                    ImageView answerImageView = new ImageView(answerImage);
                    answerImageView.setFitWidth(50);
                    answerImageView.setPreserveRatio(true);

                    // Gộp RadioButton và ImageView trong HBox
                    HBox answerBox = new HBox(10, radioButton, answerImageView);
                    answerContainer.getChildren().add(answerBox);
                } catch (Exception e) {
                System.err.println("Không thể load ảnh của đáp án: " + e.getMessage());
                answerContainer.getChildren().add(radioButton);
            }
            } else {
                answerContainer.getChildren().add(radioButton);
            }
        }
    }
}
