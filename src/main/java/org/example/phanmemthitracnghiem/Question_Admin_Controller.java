package org.example.phanmemthitracnghiem;

import BUS.AnswerBUS;
import BUS.QuestionBUS;
import DAO.QuestionDAO;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import DTO.TopicDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Question_Admin_Controller {

    private AdminController adminController;

    @FXML
    private ImageView btnAdd;

    @FXML
    private Button btnAddImage;

    @FXML
    private ImageView btnDelete;

    @FXML
    private ImageView btnUpdate;

    @FXML
    private ComboBox<?> cbLevel;

    @FXML
    private ComboBox<?> cbTopicID;
    @FXML
    private TableView<QuestionDTO> tableQuestions; // ✅ Sửa kiểu dữ liệu
    @FXML
    private TableColumn<QuestionDTO, Integer> colID;
    @FXML
    private TableColumn<QuestionDTO, String> colContent;
    @FXML
    private TableColumn<QuestionDTO, String> colLevel;
    @FXML
    private TableColumn<QuestionDTO, Integer> colStatus;

    @FXML
    private TextField txtAnswerA, txtAnswerB, txtAnswerC, txtAnswerD, txtAnswerE;
    @FXML
    private RadioButton rbA, rbB, rbC, rbD, rbE;
    @FXML
    private ToggleGroup answerGroup;
    @FXML
    private Button btnAddAnswer, btnUpdateAnswer, btnDeleteAnswer;
    private int selectedQuestionID = -1;
    private List<AnswerDTO> currentAnswers;
    @FXML
    private TextField txtContent;

    @FXML
    private ImageView txtPictures; // Đảm bảo import từ javafx.scene.image.ImageView

    private File selectedFile;

    @FXML
    public void initialize() {
        currentAnswers = new ArrayList<>(); // 🔥 Fix lỗi bị null

        txtPictures.setVisible(false);
        rbA.setToggleGroup(answerGroup);
        rbB.setToggleGroup(answerGroup);
        rbC.setToggleGroup(answerGroup);
        rbD.setToggleGroup(answerGroup);
        rbE.setToggleGroup(answerGroup);

        // Liên kết dữ liệu TableView
        if (colID != null && colContent != null && colLevel != null && colStatus != null) {
            colID.setCellValueFactory(new PropertyValueFactory<>("qID"));
            colContent.setCellValueFactory(new PropertyValueFactory<>("qContent"));
            colLevel.setCellValueFactory(new PropertyValueFactory<>("qLevel"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("qStatus"));
        } else {
            System.err.println("TableColumns chưa được khởi tạo trong FXML!");
        }

        // Bắt sự kiện chọn câu hỏi
        // Bắt sự kiện chọn một câu hỏi trong bảng
        tableQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedQuestionID = newSelection.getQID();
                txtContent.setText(newSelection.getQContent());
                loadAnswers(selectedQuestionID); // 🔥 Load câu trả lời của câu hỏi được chọn
            }
        });


        loadQuestions();
    }
    private boolean validateAnswers() {
        if (currentAnswers == null || currentAnswers.isEmpty()) {
            showAlert("Câu hỏi phải có ít nhất 2 câu trả lời!");
            return false;
        }

        int count = 0;
        for (AnswerDTO ans : currentAnswers) {
            if (!ans.getAwContent().trim().isEmpty()) {
                count++;
            }
        }
        if (count < 2) {
            showAlert("Câu hỏi phải có ít nhất 2 câu trả lời!");
            return false;
        }
        if (count > 5) {
            showAlert("Câu hỏi không được có quá 5 câu trả lời!");
            return false;
        }
        return true;
    }
    @FXML
    private void handleAddAnswer() {
        if (txtContent.getText().trim().isEmpty()) {
            showAlert("Vui lòng nhập nội dung câu hỏi!");
            return;
        }

        // ✅ Nếu `ComboBox` chưa có dữ liệu, đặt giá trị mặc định là "1"
        String level = (cbLevel.getValue() != null) ? cbLevel.getValue().toString() : "1";

        int topicID;
        try {
            topicID = (cbTopicID.getValue() != null) ? Integer.parseInt(cbTopicID.getValue().toString()) : 1;
        } catch (NumberFormatException e) {
            topicID = 1; // 🔥 Nếu không phải số, đặt mặc định là 1
        }

        // 🔥 Tạo câu hỏi mới (QuestionDTO)
        QuestionDTO newQuestion = new QuestionDTO(
                0,  // qID (mới nên đặt 0)
                txtContent.getText(), // Nội dung câu hỏi
                "", // Không có hình ảnh
                topicID, // ID chủ đề
                level, // Mức độ
                1  // Trạng thái mặc định là 1 (hiển thị)
        );

        // 🔥 Lưu câu hỏi vào database
        boolean isAdded = QuestionBUS.getInstance().addQuestion(newQuestion);
        if (!isAdded) {
            showAlert("Thêm câu hỏi thất bại!");
            return;
        }

        int newQuestionID = QuestionBUS.getInstance().getLastInsertID();

        List<AnswerDTO> answerList = new ArrayList<>();
        if (!txtAnswerA.getText().trim().isEmpty()) {
            answerList.add(new AnswerDTO(0, newQuestionID, txtAnswerA.getText(), "", rbA.isSelected() ? 1 : 0, 1));
        }
        if (!txtAnswerB.getText().trim().isEmpty()) {
            answerList.add(new AnswerDTO(0, newQuestionID, txtAnswerB.getText(), "", rbB.isSelected() ? 1 : 0, 1));
        }
        if (!txtAnswerC.getText().trim().isEmpty()) {
            answerList.add(new AnswerDTO(0, newQuestionID, txtAnswerC.getText(), "", rbC.isSelected() ? 1 : 0, 1));
        }
        if (!txtAnswerD.getText().trim().isEmpty()) {
            answerList.add(new AnswerDTO(0, newQuestionID, txtAnswerD.getText(), "", rbD.isSelected() ? 1 : 0, 1));
        }
        if (!txtAnswerE.getText().trim().isEmpty()) {
            answerList.add(new AnswerDTO(0, newQuestionID, txtAnswerE.getText(), "", rbE.isSelected() ? 1 : 0, 1));
        }

        if (answerList.size() < 2) {
            showAlert("Câu hỏi phải có ít nhất 2 câu trả lời!");
            return;
        }

        boolean answersAdded = AnswerBUS.getInstance().addAnswers(answerList);
        if (!answersAdded) {
            showAlert("Lưu câu trả lời thất bại!");
            return;
        }

        loadQuestions();
        loadAnswers(newQuestionID);
        showAlert("Thêm câu hỏi và câu trả lời thành công!");
    }

    @FXML
    private void handleUpdateAnswer() {
        if (selectedQuestionID == -1) {
            showAlert("⚠️ Vui lòng chọn một câu hỏi trước!");
            return;
        }

        // ✅ Cập nhật nội dung câu hỏi từ TextField
        String updatedQuestionContent = txtContent.getText().trim();
        if (updatedQuestionContent.isEmpty()) {
            showAlert("⚠️ Nội dung câu hỏi không được để trống!");
            return;
        }

        // ✅ Cập nhật Level và Topic (mặc định 1 nếu không chọn)
        String updatedLevel = (cbLevel.getValue() != null) ? cbLevel.getValue().toString() : "1";
        int updatedTopicID = (cbTopicID.getValue() != null) ? Integer.parseInt(cbTopicID.getValue().toString()) : 1;

        // ✅ Tạo đối tượng QuestionDTO để cập nhật
        QuestionDTO updatedQuestion = new QuestionDTO(
                selectedQuestionID,
                updatedQuestionContent,
                "", // Ảnh (nếu có thể thêm)
                updatedTopicID,
                updatedLevel,
                1 // Trạng thái mặc định
        );

        // 🔥 Cập nhật câu hỏi trong DB
        boolean questionUpdated = QuestionBUS.getInstance().updateQuestion(updatedQuestion);

        // ✅ Cập nhật danh sách câu trả lời
        for (int i = 0; i < currentAnswers.size(); i++) {
            AnswerDTO answer = currentAnswers.get(i);

            switch (i) {
                case 0:
                    answer.setAwContent(txtAnswerA.getText());
                    answer.setIsRight(rbA.isSelected() ? 1 : 0);
                    break;
                case 1:
                    answer.setAwContent(txtAnswerB.getText());
                    answer.setIsRight(rbB.isSelected() ? 1 : 0);
                    break;
                case 2:
                    answer.setAwContent(txtAnswerC.getText());
                    answer.setIsRight(rbC.isSelected() ? 1 : 0);
                    break;
                case 3:
                    answer.setAwContent(txtAnswerD.getText());
                    answer.setIsRight(rbD.isSelected() ? 1 : 0);
                    break;
                case 4:
                    answer.setAwContent(txtAnswerE.getText());
                    answer.setIsRight(rbE.isSelected() ? 1 : 0);
                    break;
            }

            // 🔥 Cập nhật câu trả lời trong DB
            AnswerBUS.getInstance().updateAnswer(answer);
        }

        // 🟢 Hiển thị thông báo thành công nếu mọi thứ OK
        if (questionUpdated) {
            showAlert("✅ Cập nhật câu hỏi và câu trả lời thành công!");
            loadQuestions(); // Làm mới danh sách câu hỏi
            loadAnswers(selectedQuestionID); // Làm mới danh sách câu trả lời
        } else {
            showAlert("❌ Lỗi khi cập nhật câu hỏi!");
        }
    }

    @FXML
    private void handleDeleteAnswer() {
        if (selectedQuestionID == -1) {
            showAlert("⚠️ Vui lòng chọn một câu hỏi trước khi xóa!");
            return;
        }

        // 🔥 Hiển thị hộp thoại xác nhận
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Xác nhận xóa");
        confirmDialog.setHeaderText("Bạn có chắc chắn muốn xóa câu hỏi này?");
        confirmDialog.setContentText("Hành động này sẽ xóa cả câu hỏi và các câu trả lời liên quan!");

        ButtonType btnYes = new ButtonType("Xóa", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmDialog.getButtonTypes().setAll(btnYes, btnNo);

        // 🟢 Nếu người dùng xác nhận xóa
        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == btnYes) {
                // 1️⃣ Xóa toàn bộ câu trả lời của câu hỏi
                boolean answersDeleted = AnswerBUS.getInstance().deleteAnswersByQuestionID(selectedQuestionID);

                // 2️⃣ Xóa câu hỏi khỏi DB
                boolean questionDeleted = QuestionBUS.getInstance().deleteQuestion(selectedQuestionID);

                // 3️⃣ Kiểm tra kết quả và cập nhật UI
                if (answersDeleted && questionDeleted) {
                    showAlert("✅ Đã xóa câu hỏi và câu trả lời thành công!");
                    selectedQuestionID = -1;
                    loadQuestions(); // Làm mới danh sách câu hỏi
                } else {
                    showAlert("❌ Xóa thất bại! Vui lòng thử lại.");
                }
            }
        });
    }


    @FXML
    private void addQuestion() {
        if (txtContent.getText().trim().isEmpty()) {
            showAlert("Vui lòng nhập nội dung câu hỏi!");
            return;
        }

        if (!validateAnswers()) return;

        QuestionDTO newQuestion = new QuestionDTO(
                0, txtContent.getText(), "", 1, cbLevel.getValue().toString(), 1
        );
        boolean isSuccess = QuestionBUS.getInstance().addQuestion(newQuestion);

        if (isSuccess) {
            int newQuestionID = QuestionBUS.getInstance().getLastInsertID();
            saveAnswers(newQuestionID);
            showAlert("Thêm câu hỏi thành công!");
            loadQuestions();
        } else {
            showAlert("Thêm câu hỏi thất bại!");
        }
    }
    /**
     * ✅ Lưu danh sách câu trả lời vào database
     */
    private void saveAnswers(int questionID) {
        for (int i = 0; i < currentAnswers.size(); i++) {
            AnswerDTO answer = currentAnswers.get(i);
            answer.setQID(questionID);
            answer.setAwStatus(1);

            if (i == 0 && rbA.isSelected()) answer.setIsRight(1);
            if (i == 1 && rbB.isSelected()) answer.setIsRight(1);
            if (i == 2 && rbC.isSelected()) answer.setIsRight(1);
            if (i == 3 && rbD.isSelected()) answer.setIsRight(1);
            if (i == 4 && rbE.isSelected()) answer.setIsRight(1);

            AnswerBUS.getInstance().addAnswer(answer);
        }
    }
    private void loadQuestions() {
        List<QuestionDTO> questions = QuestionBUS.getInstance().getAllQuestions();
        ObservableList<QuestionDTO> observableList = FXCollections.observableArrayList(questions);
        tableQuestions.setItems(observableList);
    }
    /**
     * ✅ Load câu trả lời khi chọn câu hỏi
     */
    private void loadAnswers(int questionID) {
        // 🔥 Kiểm tra nếu không có ID hợp lệ
        if (questionID == -1) {
            System.out.println("⚠️ Không có câu hỏi nào được chọn!");
            return;
        }

        // 🔥 Lấy danh sách câu trả lời từ Database
        currentAnswers = AnswerBUS.getInstance().getAnswersByQuestionID(questionID);

        // 🛠 Xóa nội dung cũ trước khi cập nhật
        txtAnswerA.clear();
        txtAnswerB.clear();
        txtAnswerC.clear();
        txtAnswerD.clear();
        txtAnswerE.clear();
        rbA.setSelected(false);
        rbB.setSelected(false);
        rbC.setSelected(false);
        rbD.setSelected(false);
        rbE.setSelected(false);

        // 📝 In ra danh sách câu trả lời để kiểm tra
        System.out.println("📌 Câu trả lời của câu hỏi ID " + questionID + ": " + currentAnswers.size());

        // 🔄 Hiển thị câu trả lời lên UI
        for (int i = 0; i < currentAnswers.size(); i++) {
            AnswerDTO answer = currentAnswers.get(i);
            switch (i) {
                case 0:
                    txtAnswerA.setText(answer.getAwContent());
                    rbA.setSelected(answer.getIsRight() == 1);
                    break;
                case 1:
                    txtAnswerB.setText(answer.getAwContent());
                    rbB.setSelected(answer.getIsRight() == 1);
                    break;
                case 2:
                    txtAnswerC.setText(answer.getAwContent());
                    rbC.setSelected(answer.getIsRight() == 1);
                    break;
                case 3:
                    txtAnswerD.setText(answer.getAwContent());
                    rbD.setSelected(answer.getIsRight() == 1);
                    break;
                case 4:
                    txtAnswerE.setText(answer.getAwContent());
                    rbE.setSelected(answer.getIsRight() == 1);
                    break;
            }
        }
    }



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleAddImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn hình ảnh");

        // Chỉ chọn file ảnh (PNG, JPG, JPEG)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg")
        );

        // Mở hộp thoại chọn file
        Stage stage = (Stage) btnAddImage.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            txtPictures.setImage(image); // Đặt ảnh vào ImageView
            txtPictures.setVisible(true); // Hiển thị ảnh
            btnAddImage.setVisible(false); // Ẩn nút "Add Image"
        }
    }
}
