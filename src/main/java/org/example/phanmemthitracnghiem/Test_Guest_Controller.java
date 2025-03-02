package org.example.phanmemthitracnghiem;

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
            VBox testItem = createTestCodeItem("Lý - A", 20, 2, 30, 25);
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
    private VBox createTestCodeItem(String testTitle, int num_questions, int testLimit, int testTime, int testDate ) {
        Label title = new Label(testTitle);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Arial'");

        Label numTitle = new Label("Số câu: ");
        numTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman'");
        Label num = new Label(String.valueOf(num_questions));
        num.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        HBox hb = new HBox(numTitle, num);
        hb.setAlignment(Pos.CENTER_LEFT);

        Label timeTitle = new Label("Thời gian làm bài: ");
        timeTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman'");
        Label time = new Label(String.valueOf(testTime));
        time.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        HBox hb_1 = new HBox(timeTitle, time);
        hb_1.setAlignment(Pos.CENTER_LEFT);

        Label dateTitle = new Label("Thời gian mở: ");
        dateTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman'");
        Label date = new Label(String.valueOf(testDate));
        date.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        HBox hb_2 = new HBox(dateTitle, date);
        hb_2.setAlignment(Pos.CENTER_LEFT);

        Button button = new Button("Làm bài");
        button.setOnAction(e -> switchToExamScene(button,testTitle,testTime,num_questions));
        VBox vBox = new VBox(title, hb, hb_1, hb_2, button);
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
