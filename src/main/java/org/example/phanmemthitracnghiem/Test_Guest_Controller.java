package org.example.phanmemthitracnghiem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Test_Guest_Controller implements Initializable{

    @FXML
    private GridPane gp;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> testNames = List.of(
                "Đề Toán 1", "Đề Lý 1", "Đề Hóa 1", "Đề Anh 1",
                "Đề Sinh 1", "Đề Sử 1", "Đề Địa 1", "Đề Tin 1",
                "Đề Toán 1", "Đề Lý 1", "Đề Hóa 1", "Đề Anh 1",
                "Đề Sinh 1", "Đề Sử 1", "Đề Địa 1", "Đề Tin 1"
        );

        int columns = 3; // Số cột mong muốn
        int row = 0, col = 0;

        for (String testName : testNames) {
            VBox testItem = createTestItem(testName);
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

    // Tạo style đề thi
    private VBox createTestItem(String testName) {
        Label label = new Label(testName);
        Button button = new Button("Làm bài");

        VBox hBox = new VBox(label, button);
        hBox.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-color: #f8f8f8;");
        hBox.setPrefHeight(200);
        return hBox;

    }

}
