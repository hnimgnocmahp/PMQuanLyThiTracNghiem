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

public class Topic_Admin_Controller {

    private TopicBUS topicBUS = TopicBUS.getInstance();
    private TopicDTO topic;

    @FXML
    private TableColumn<?, ?> idLabel;

    @FXML
    private TableColumn<?, ?> parentIdLabel;

    @FXML
    private TableColumn<?, ?> statusLabel;

    @FXML
    private TableView<TopicDTO> tableView;

    @FXML
    private TextField titleField;

    @FXML
    private TableColumn<?, ?> titleLabel;

    @FXML
    private ComboBox<Integer> titleParentField = new ComboBox<>();


    @FXML
    public void initialize(){
        idLabel.setCellValueFactory(new PropertyValueFactory<>("topicID"));
        titleLabel.setCellValueFactory(new PropertyValueFactory<>("topicTitle"));
        parentIdLabel.setCellValueFactory(new PropertyValueFactory<>("topicParent"));
        statusLabel.setCellValueFactory(new PropertyValueFactory<>("topicStatus"));
        titleParentField.getItems().addAll(1, 2, 3, 4, 5);
        loadTopics();
    }

    @FXML
    void addTopic(MouseEvent event) {
        if(titleField.getText().equals("") || titleParentField.getSelectionModel().getSelectedItem() == null){
            LoginController.showAlert("Nhắc nhở","Vui lòng điền đầy đủ thông tin");
        }

        String title = titleField.getText();
        int titleParent = titleParentField.getSelectionModel().getSelectedItem();
        TopicDTO topic1 = new TopicDTO(title,titleParent,1);
        if (topicBUS.addTopic(topic1) < 0){
            LoginController.showAlert("Thông báo", "Thêm thất bại");
        }
        else{
            LoginController.showAlert("Thông báo", "Thêm thành công");
            reset();
            loadTopics();
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
        topic = tableView.getItems().get(row);
        topic.setTopicTitle(titleField.getText());
        topic.setTopicParent(titleParentField.getSelectionModel().getSelectedItem());
        if (topicBUS.updateTopic(topic) < 0){
            LoginController.showAlert("Thông báo", "Sửa thất bại");
        }
        else{
            LoginController.showAlert("Thông báo", "Sửa thành công");
            reset();
            loadTopics();
        }
    }

    @FXML
    TopicDTO getRow(MouseEvent event) {
        TopicDTO topicDTO = (TopicDTO) tableView.getSelectionModel().getSelectedItem();
        if (topicDTO != null){
            titleField.setText(topicDTO.getTopicTitle());
            titleParentField.setValue(topicDTO.getTopicParent());
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



}
