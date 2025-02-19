package org.example.phanmemthitracnghiem;

import BUS.TopicBUS;
import DTO.TopicDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Topic_Admin_Controller {

    private TopicBUS topicBUS = TopicBUS.getInstance();
    private TopicDTO topic;

    @FXML
    private TableColumn<TopicDTO, Integer> idLabel;

    @FXML
    private TableColumn<TopicDTO, Integer> parentIdLabel;

    @FXML
    private TableColumn<TopicDTO, Integer> statusLabel;

    @FXML
    private TableView<TopicDTO> tableView;

    @FXML
    private TextField titleField;

    @FXML
    private TableColumn<TopicDTO, String> titleLabel;

    @FXML
    private ComboBox<String> titleParentField = new ComboBox<>();

    private ObservableList<String> ParentIDList = FXCollections.observableArrayList();


    @FXML
    public void initialize(){
        idLabel.setCellValueFactory(new PropertyValueFactory<>("topicID"));
        titleLabel.setCellValueFactory(new PropertyValueFactory<>("topicTitle"));
        parentIdLabel.setCellValueFactory(new PropertyValueFactory<>("topicParent"));
        statusLabel.setCellValueFactory(new PropertyValueFactory<>("topicStatus"));

        loadTopicsParent();
        titleParentField.setItems(ParentIDList);
        loadTopics();
    }

    @FXML
    void addTopic(MouseEvent event) {
        if (titleField.getText().equals("")) {
            LoginController.showAlert("Nhắc nhở", "Vui lòng điền đầy đủ thông tin");
            return;
        }


        String title = titleField.getText();

        String selectedValue = titleParentField.getSelectionModel().getSelectedItem();
        int titleParent;
        if (selectedValue == null || selectedValue.startsWith("0 -")) {
            titleParent = 0;
        } else {
            titleParent = Integer.parseInt(selectedValue.split(" - ")[0]);
        }

        if(!topicBUS.Validation(titleParent,title)){
            LoginController.showAlert("Nhắc nhở", "Thông tin không hợp lệ");
            return;
        }

        TopicDTO topic1 = new TopicDTO(title, titleParent, 1);
        if (topicBUS.addTopic(topic1) < 0) {
            LoginController.showAlert("Thông báo", "Thêm thất bại");
        } else {
            LoginController.showAlert("Thông báo", "Thêm thành công");
            reset();
            loadTopics();
            loadTopicsParent(); // Cập nhật lại ComboBox nếu có mục mới
        }
    }


    @FXML
    void cancelTopic(MouseEvent event) {
        reset();
        loadTopics();
    }

    @FXML
    void deleteTopic(MouseEvent event) {
        int row = tableView.getSelectionModel().getSelectedIndex();
        if(topicBUS.deleteTopic2(tableView.getItems().get(row).getTopicID()) < 0){
            LoginController.showAlert("Thông báo", "Xóa thất bại");
        }
        else{
            LoginController.showAlert("Thông báo", "Xóa thành công");
            reset();
            loadTopics();
        }
    }

    @FXML
    void updateTopic(MouseEvent event) {
        int row = tableView.getSelectionModel().getSelectedIndex();
        if (row == -1) {
            LoginController.showAlert("Nhắc nhở", "Vui lòng chọn dòng cần sửa");
            return;
        }

        topic = tableView.getItems().get(row);
        topic.setTopicTitle(titleField.getText());

        String selectedValue = titleParentField.getSelectionModel().getSelectedItem();
        int titleParent;
        if (selectedValue == null || selectedValue.startsWith("0 -")) {
            titleParent = 0;
        } else {
            titleParent = Integer.parseInt(selectedValue.split(" - ")[0]);
        }

        topic.setTopicParent(titleParent);

        System.out.println(topicBUS.Validation(titleParent,titleField.getText()));

        if(!topicBUS.Validation(titleParent,titleField.getText())){
            LoginController.showAlert("Nhắc nhở", "Thông tin không hợp lệ");
            return;
        }


        if (topicBUS.updateTopic(topic) < 0) {
            LoginController.showAlert("Thông báo", "Sửa thất bại");
        } else {
            LoginController.showAlert("Thông báo", "Sửa thành công");
            reset();
            loadTopics();
            loadTopicsParent(); // Cập nhật lại combobox
        }
    }


    @FXML
    TopicDTO getRow(MouseEvent event) {
        TopicDTO topicDTO = (TopicDTO) tableView.getSelectionModel().getSelectedItem();
        if (topicDTO != null){
            titleField.setText(topicDTO.getTopicTitle());
            titleParentField.setValue(topicDTO.getTopicParent() + " - " + topicBUS.searchTopicByID(topicDTO.getTopicParent()).getTopicTitle());
        }
        return topic = topicDTO;
    }

    private void reset(){
        titleField.setText("");
        titleParentField.setValue(null);
        topic = new TopicDTO();
    }

    private void loadTopics(){
        ObservableList<TopicDTO> observableList = FXCollections.observableArrayList(topicBUS.loadTopics());
        tableView.setItems(observableList);
    }

    private void loadTopicsParent() {
        List<String> tempList = new ArrayList<>(ParentIDList); // Copy ra danh sách tạm
        if (!tempList.contains("0 - null")) {
            tempList.add("0 - null");
        }

        for (TopicDTO topicDTO : topicBUS.loadTopics()) {
            int topicParent = topicDTO.getTopicID();
            boolean isExist = false;
            for (String parent : tempList) {
                if (parent.startsWith(topicParent + " -")) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                String topicTitle = topicBUS.searchTopicByID(topicParent).getTopicTitle();
                tempList.add(topicParent + " - " + topicTitle);
            }
        }
        ParentIDList.setAll(tempList); // Cập nhật lại toàn bộ
    }
}
