package BUS;

import DAO.TestCodeDAO;
import DAO.UserDAO;
import DTO.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TestCodeBUS {

    private static TestCodeBUS instance;

    private TestCodeDAO testCodeDAO;

    private TestCodeBUS() {
        testCodeDAO = new TestCodeDAO();
    }

    public static TestCodeBUS getInstance() {
        if (instance == null) {
            synchronized (TestCodeBUS.class) {
                if (instance == null) {
                    instance = new TestCodeBUS();
                }
            }
        }
        return instance;
    }

    public int add(TestCodeDTO testCodeDTO) {
        int result = testCodeDAO.add(testCodeDTO);

        return result;
    }

    public int update(TestCodeDTO testCodeDTO) {
        return testCodeDAO.update(testCodeDTO);
    }

    public int delete(String id) {
        return testCodeDAO.delete(id);
    }

    public TestCodeDTO getTestCodeByID(String id) {
        return testCodeDAO.getTestCodeById(id);
    }

    public TestCodeDTO getRandomTestCode(String testCode) {
        return testCodeDAO.getRandomTestCodeByTestCode(testCode);
    }

    public List<String> getAllExCode(){return testCodeDAO.getAllExCode();}

    public void exportWord(String testCode, Stage stage) {
        try {
            // Lấy thông tin đề thi
            TestDTO testDTO = TestBUS.getInstance().selectTestByTestCode(testCode);
            TestCodeDTO testCodeDTO = TestCodeBUS.getInstance().getRandomTestCode(testCode);

            // Lấy danh sách câu hỏi
            String ex_quesID = testCodeDTO.getEx_questionIDs();
            String[] questionIDs = ex_quesID.split(",");

            // Mở hộp thoại chọn thư mục và đặt tên file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn nơi lưu file Word");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Documents (*.docx)", "*.docx"));

            // Đặt tên mặc định cho file
            fileChooser.setInitialFileName("Test_" + testCode + ".docx");

            File file = fileChooser.showSaveDialog(stage);
            if (file == null) {
                return;
            }

            // Tạo file Word
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(file);

            // Tiêu đề đề thi
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun runTitle = title.createRun();
            runTitle.setBold(true);
            runTitle.setFontSize(18);
            runTitle.setText("Test: " + testDTO.getTestTitle());
            runTitle.addBreak();

            // Thông tin TestCode và TestTime
            XWPFParagraph info = document.createParagraph();
            XWPFRun runInfo = info.createRun();
            runInfo.setFontSize(14);
            runInfo.setText("Test Code: " + testDTO.getTestCode());
            runInfo.addBreak();
            runInfo.setText("Test Time: " + testDTO.getTestTime() + " minutes");
            runInfo.addBreak();
            runInfo.addBreak();
            String str;
            int numQ = 1;

            // Duyệt từng câu hỏi và câu trả lời
            for (String number : questionIDs) {
                int questionID = Integer.parseInt(number.trim());
                QuestionDTO question = QuestionBUS.getInstance().getQuestionById(questionID);
                List<AnswerDTO> answers = AnswerBUS.getInstance().getAnswersByQuestionID(questionID);

                // Ghi câu hỏi vào Word
                XWPFParagraph questionParagraph = document.createParagraph();
                XWPFRun runQuestion = questionParagraph.createRun();
                runQuestion.setBold(true);
                runQuestion.setFontSize(14);
                runQuestion.setText("Q" + numQ + ": " + question.getQContent());
                numQ++;

                str = "A";
                // Ghi danh sách câu trả lời
                for (AnswerDTO answer : answers) {
                    XWPFParagraph answerParagraph = document.createParagraph();
                    XWPFRun runAnswer = answerParagraph.createRun();
                    runAnswer.setFontSize(12);
                    runAnswer.setText(str + ". " + answer.getAwContent());
                    runAnswer.addBreak();
                    char ch = str.charAt(0);
                    ch = (char) (ch + 1);
                    str = String.valueOf(ch);
                }
                runQuestion.addBreak();
            }

            // Lưu file
            document.write(out);
            out.close();
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
