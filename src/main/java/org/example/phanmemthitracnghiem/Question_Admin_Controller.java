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
    private ComboBox<String> cbLevel;

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
        currentAnswers = new ArrayList<>();
        txtPictures.setVisible(false);

        // ✅ Cấu hình RadioButton nhóm câu trả lời
        rbA.setToggleGroup(answerGroup);
        rbB.setToggleGroup(answerGroup);
        rbC.setToggleGroup(answerGroup);
        rbD.setToggleGroup(answerGroup);
        rbE.setToggleGroup(answerGroup);

        // ✅ Cấu hình dữ liệu cho ComboBox Level (Dễ - Trung bình - Khó)
        ObservableList<String> levelOptions = FXCollections.observableArrayList("Dễ", "Trung bình", "Khó");
        cbLevel.setItems(levelOptions);
        cbLevel.getSelectionModel().select(0); // Mặc định là "Dễ"

        // ✅ Cấu hình TableView
        colID.setCellValueFactory(new PropertyValueFactory<>("qID"));
        colContent.setCellValueFactory(new PropertyValueFactory<>("qContent"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("qLevel"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("qStatus"));

        // ✅ Sự kiện khi chọn một câu hỏi trong bảng
        tableQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedQuestionID = newSelection.getQID();
                txtContent.setText(newSelection.getQContent());

                // ✅ Đặt giá trị Level từ số (1,2,3) thành chữ
                cbLevel.setValue(switch (newSelection.getQLevel()) {
                    case "1" -> "Dễ";
                    case "2" -> "Trung bình";
                    case "3" -> "Khó";
                    default -> "Dễ";
                });

                loadAnswers(selectedQuestionID);
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
    } @FXML
    private void handleAddAnswer() {
        if (txtContent.getText().trim().isEmpty()) {
            showAlert("Vui lòng nhập nội dung câu hỏi!");
            return;
        }

        // ✅ Chuyển đổi giá trị Level từ chữ sang số (1, 2, 3)
        int level = switch (cbLevel.getValue()) {
            case "Dễ" -> 1;
            case "Trung bình" -> 2;
            case "Khó" -> 3;
            default -> 1;
        };

        int topicID = (cbTopicID.getValue() != null) ? Integer.parseInt(cbTopicID.getValue().toString()) : 1;

        // ✅ Tạo câu hỏi mới
        QuestionDTO newQuestion = new QuestionDTO(0, txtContent.getText(), "", topicID, String.valueOf(level), 1);

        boolean isAdded = QuestionBUS.getInstance().addQuestion(newQuestion);
        if (!isAdded) {
            showAlert("Thêm câu hỏi thất bại!");
            return;
        }

        int newQuestionID = QuestionBUS.getInstance().getLastInsertID();

        List<AnswerDTO> answerList = createAnswerList(newQuestionID);
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
        showAlert("✅ Thêm câu hỏi và câu trả lời thành công!");
    }

    /**
     * 🔥 Chuyển đổi giá trị Level từ String (Dễ, Trung bình, Khó) sang số (1, 2, 3)
     */
    private int convertLevelToNumber(String levelText) {
        return switch (levelText) {
            case "Dễ" -> 1;
            case "Trung bình" -> 2;
            case "Khó" -> 3;
            default -> 1;
        };
    }

    /**
     * 🔥 Chuyển đổi giá trị Level từ số (1,2,3) sang chữ (Dễ, Trung bình, Khó)
     */
    private String convertLevelToText(String level) {
        return switch (level) {
            case "1" -> "Dễ";
            case "2" -> "Trung bình";
            case "3" -> "Khó";
            default -> "Dễ";
        };
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

        // ✅ Chuyển đổi giá trị Level từ chữ sang số (1, 2, 3)
        int updatedLevel = convertLevelToNumber(cbLevel.getValue());

        // ✅ Cập nhật Topic ID (nếu có)
        int updatedTopicID = (cbTopicID.getValue() != null) ? Integer.parseInt(cbTopicID.getValue().toString()) : 1;

        // ✅ Tạo đối tượng QuestionDTO để cập nhật
        QuestionDTO updatedQuestion = new QuestionDTO(
                selectedQuestionID,
                updatedQuestionContent,
                "", // Ảnh (nếu có thể thêm sau)
                updatedTopicID,
                String.valueOf(updatedLevel), // Lưu số thay vì chữ!
                1 // Trạng thái mặc định là 1
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
            showAlert("⚠️ Vui lòng chọn một câu hỏi trước khi ẩn!");
            return;
        }

        // 🔥 Hiển thị hộp thoại xác nhận
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Xác nhận ẩn câu hỏi");
        confirmDialog.setHeaderText("Bạn có chắc chắn muốn ẩn câu hỏi này?");
        confirmDialog.setContentText("Hành động này sẽ đặt trạng thái của câu hỏi và câu trả lời về 0, nhưng không xóa khỏi hệ thống.");

        ButtonType btnYes = new ButtonType("Ẩn", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmDialog.getButtonTypes().setAll(btnYes, btnNo);

        // 🟢 Nếu người dùng xác nhận ẩn câu hỏi
        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == btnYes) {
                // 1️⃣ Cập nhật trạng thái tất cả câu trả lời về 0
                boolean answersUpdated = AnswerBUS.getInstance().setAnswersStatusByQuestionID(selectedQuestionID, 0);

                // 2️⃣ Cập nhật trạng thái của câu hỏi về 0
                boolean questionUpdated = QuestionBUS.getInstance().setQuestionStatus(selectedQuestionID, 0);

                // 3️⃣ Kiểm tra kết quả và cập nhật UI
                if (answersUpdated && questionUpdated) {
                    showAlert("✅ Đã ẩn câu hỏi và câu trả lời thành công!");
                    selectedQuestionID = -1;
                    loadQuestions(); // Làm mới danh sách câu hỏi
                } else {
                    showAlert("❌ Ẩn thất bại! Vui lòng thử lại.");
                }
            }
        });
    }

    @FXML
    private void handleClearFields() {
        txtContent.clear();
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

        cbLevel.getSelectionModel().select(0);
        cbTopicID.getSelectionModel().clearSelection();

        currentAnswers.clear();
        txtPictures.setVisible(false);
        txtPictures.setImage(null);

        showAlert("✅ Đã xóa toàn bộ nội dung nhập!");
    }

    private List<AnswerDTO> createAnswerList(int questionID) {
        List<AnswerDTO> answers = new ArrayList<>();

        if (!txtAnswerA.getText().trim().isEmpty()) {
            answers.add(new AnswerDTO(0, questionID, txtAnswerA.getText(), "", rbA.isSelected() ? 1 : 0, 1));
        }
        if (!txtAnswerB.getText().trim().isEmpty()) {
            answers.add(new AnswerDTO(0, questionID, txtAnswerB.getText(), "", rbB.isSelected() ? 1 : 0, 1));
        }
        if (!txtAnswerC.getText().trim().isEmpty()) {
            answers.add(new AnswerDTO(0, questionID, txtAnswerC.getText(), "", rbC.isSelected() ? 1 : 0, 1));
        }
        if (!txtAnswerD.getText().trim().isEmpty()) {
            answers.add(new AnswerDTO(0, questionID, txtAnswerD.getText(), "", rbD.isSelected() ? 1 : 0, 1));
        }
        if (!txtAnswerE.getText().trim().isEmpty()) {
            answers.add(new AnswerDTO(0, questionID, txtAnswerE.getText(), "", rbE.isSelected() ? 1 : 0, 1));
        }

        return answers;
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

        // ✅ Chuyển đổi giá trị số thành chữ khi hiển thị trên UI
        for (QuestionDTO q : questions) {
            q.setQLevel(convertLevelToText(q.getQLevel())); // Chuyển 1 → "Dễ", 2 → "Trung bình", 3 → "Khó"
        }

        ObservableList<QuestionDTO> observableList = FXCollections.observableArrayList(questions);
        tableQuestions.setItems(observableList);
    }

    /**
     * ✅ Load câu trả lời khi chọn câu hỏi
     */
    private void loadAnswers(int questionID) {
        if (questionID == -1) {
            System.out.println("⚠️ Không có câu hỏi nào được chọn!");
            return;
        }

        // Lấy danh sách câu trả lời từ database
        currentAnswers = AnswerBUS.getInstance().getAnswersByQuestionID(questionID);

        // Xóa nội dung cũ
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

        // Hiển thị dữ liệu lên UI
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
