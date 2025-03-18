package org.example.phanmemthitracnghiem;

import BUS.*;
import DTO.*;
import Interface.UserAwareController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import jdk.incubator.vector.VectorOperators;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class Test_Guest_Controller implements Initializable, UserAwareController {

    private String username;


    @Override
    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @FXML
    private GridPane gp;

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int columns = 3; // Số cột mong muốn
        int row = 0, col = 0;
        int num_easy;
        int num_medium;
        int num_hard;
        String topicListTitle;
        List<TestDTO> testList = TestBUS.getInstance().getTests();
        for (TestDTO test : testList) {
            List<TestStructureDTO> structures = TestStructureBUS.getInstance().getStructureByTestCode(test.getTestCode());
            num_easy = 0;
            num_medium = 0;
            num_hard = 0;
            topicListTitle = "";
            for (TestStructureDTO structure : structures) {
                num_easy += structure.getNum_easy();
                num_medium += structure.getNum_medium();
                num_hard += structure.getNum_diff();
                topicListTitle += " " + TopicBUS.getInstance().searchTopicByID(structure.getTpID()).getTopicTitle();
            }



            VBox testItem = createTestCodeItem(test.getTestCode(), test.getTestTitle(), topicListTitle, num_easy+num_hard+num_medium, test.getTestLimit(), test.getTestTime(), test.getTestDate());
            testItem.setMaxWidth(255);
            testItem.setMaxHeight(255);

            gp.add(testItem, col, row);
            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }
        }

    }

    @FXML
    void SearchbyClick(MouseEvent event) {
        gp.getChildren().clear();
        int columns = 3; // Số cột mong muốn
        int row = 0, col = 0;
        int num_easy;
        int num_medium;
        int num_hard;
        String topicListTitle;
        String key = searchField.getText();
        TestBUS testBUS = TestBUS.getInstance();
        for (TestDTO test: testBUS.getTests()){
            if(test.getTestTitle().toUpperCase().contains(key.toUpperCase())){
                System.out.println("1");
                List<TestStructureDTO> structures = TestStructureBUS.getInstance().getStructureByTestCode(test.getTestCode());
                num_easy = 0;
                num_medium = 0;
                num_hard = 0;
                topicListTitle = "";
                for (TestStructureDTO structure : structures) {
                    num_easy += structure.getNum_easy();
                    num_medium += structure.getNum_medium();
                    num_hard += structure.getNum_diff();
                    topicListTitle += " " + TopicBUS.getInstance().searchTopicByID(structure.getTpID()).getTopicTitle();
                }



                VBox testItem = createTestCodeItem(test.getTestCode(), test.getTestTitle(), topicListTitle, num_easy+num_hard+num_medium, test.getTestLimit(), test.getTestTime(), test.getTestDate());
                testItem.setMaxWidth(255);
                testItem.setMaxHeight(255);

                gp.add(testItem, col, row);
                col++;
                if (col >= columns) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    @FXML
    void SearchbyKey(KeyEvent event) {

    }

    // Tạo style đề thi
    private VBox createTestCodeItem(String testCode, String testTitle, String topicList, int num_questions, int testLimit, int testTime, Date testDate ) {
        Label title = new Label(testTitle);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Arial'");

        Label topic = new Label("Topic:ư ");
        topic.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman'");
        Label topiclb = new Label(topicList);
        topiclb.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        HBox hb_0 = new HBox(topic, topiclb);
        hb_0.setAlignment(Pos.CENTER_LEFT);

        Label numTitle = new Label("Num: ");
        numTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman'");
        Label num = new Label(String.valueOf(num_questions));
        num.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        HBox hb_1 = new HBox(numTitle, num);
        hb_0.setAlignment(Pos.CENTER_LEFT);

        Label timeTitle = new Label("Time: ");
        timeTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman'");
        Label time = new Label(String.valueOf(testTime));
        time.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        HBox hb_2 = new HBox(timeTitle, time);
        hb_2.setAlignment(Pos.CENTER_LEFT);

        Label dateTitle = new Label("Date: ");
        dateTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman'");
        Label date = new Label(String.valueOf(testDate));
        date.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        HBox hb_3 = new HBox(dateTitle, date);
        hb_3.setAlignment(Pos.CENTER_LEFT);

        Label limitTitle = new Label("Limit: ");
        limitTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman'");
        Label limitlb = new Label(String.valueOf(testLimit));
        limitlb.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        HBox hb_4 = new HBox(limitTitle, limitlb);
        hb_4.setAlignment(Pos.CENTER_LEFT);

        // Tạo hình ảnh từ tài nguyên
        Image image = new Image(getClass().getResource("/image/printer.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30); // Điều chỉnh kích thước nếu cần
        imageView.setPreserveRatio(true);

        // Tạo button và gán icon
        Button printButton = new Button();
        printButton.setGraphic(imageView); // Đặt icon vào button
        printButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); // Làm button trong suốt

        // Gán sự kiện khi nhấn nút
        printButton.setOnAction(event -> {
            Stage stage = (Stage) printButton.getScene().getWindow();
            TestCodeBUS.getInstance().exportWord(testCode, stage);
        });


        Button button = new Button("Exercise");
        button.setOnAction(e -> switchToExamScene(button, testCode));
        VBox vBox = new VBox(title, hb_0, hb_1, hb_2, hb_3, hb_4, printButton, button);
        vBox.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-color: #f8f8f8; -fx-border-width: 2");
        vBox.setPrefHeight(200);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(12);
        vBox.setPadding(new Insets(10));
        return vBox;

    }



    public void switchToExamScene(Button button, String testCode) {
        TestDTO test = TestBUS.getInstance().selectTestByTestCode(testCode);
        if (test.getTestLimit() < 1) {
            LoginController.showAlert("Thông báo", "Bạn đã hết lượt làm đề này");
            return;
        }

        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        if (test.getTestDate().after(today)) {
            LoginController.showAlert("Thông báo", "Chưa tới ngày mở đề");
            return;
        }
        test.setTestLimit(test.getTestLimit() - 1);
        TestBUS.getInstance().updateTest(test);

        try {

            // Load giao diện bài thi
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Exam.fxml"));
            Parent root = loader.load();

            Exam_Controller examController = loader.getController();
            if (examController != null) {
                examController.setUserName(username);
                // Gọi sau khi set dữ liệu
                examController.initializeExam(testCode);
            } else {
                System.out.println("Lỗi: Không thể lấy Exam_Controller");
            }
            // Lấy stage hiện tại từ button
            Stage currentStage = (Stage) button.getScene().getWindow();
            currentStage.setScene(new Scene(root, 944, 580)); // Đặt kích thước phù hợp
            currentStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
