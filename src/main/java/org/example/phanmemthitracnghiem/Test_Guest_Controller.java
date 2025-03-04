package org.example.phanmemthitracnghiem;

import DTO.TestDTO;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Test_Guest_Controller implements Initializable{

    @FXML
    private GridPane gp;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int columns = 3; // Số cột mong muốn
        int row = 0, col = 0;
//        List<TestDTO> testList = TestB
            VBox testItem = createTestCodeItem("a", 9   , 20, 2, 30, 25);
            testItem.setMaxWidth(255);
            testItem.setMaxHeight(255);

            gp.add(testItem, col, row);
            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }

    }

    // Tạo style đề thi
    private VBox createTestCodeItem(String testTitle, int tpID, int num_questions, int testLimit, int testTime, int testDate ) {
        Label title = new Label(testTitle);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Arial'");

        Label topic = new Label("Topic: ");
        topic.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman'");
        Label topiclb = new Label(String.valueOf(tpID));
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

        Button button = new Button("Exercise");
        button.setOnAction(e -> switchToExamScene(button,testTitle,testTime,num_questions));
        VBox vBox = new VBox(title, hb_0, hb_1, hb_2, hb_3, hb_4, button);
        vBox.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-color: #f8f8f8; -fx-border-width: 2");
        vBox.setPrefHeight(200);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(12);
        vBox.setPadding(new Insets(10));
        return vBox;

    }



    public void switchToExamScene(Button button, String title, int time, int question) {
        try {
            // Load giao diện bài thi
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Exam.fxml"));
            Parent root = loader.load();

            Exam_Controller examController = loader.getController();
            if (examController != null) {
                examController.setQuestions(question);
                examController.setTime(time);
                examController.setTitle(title);

                // Gọi sau khi set dữ liệu
                examController.initializeExam();
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
