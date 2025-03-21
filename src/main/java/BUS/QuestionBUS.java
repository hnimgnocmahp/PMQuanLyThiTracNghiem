package BUS;

import DAO.QuestionDAO;
import DTO.QuestionDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionBUS {
    private static QuestionBUS instance;
    private final QuestionDAO questionDAO;
    private static final Logger logger = Logger.getLogger(QuestionBUS.class.getName());

    public QuestionBUS() {
        this.questionDAO = QuestionDAO.getInstance(); // Sử dụng Singleton cho DAO
    }

    public static QuestionBUS getInstance() {
        if (instance == null) {
            synchronized (QuestionBUS.class) {
                if (instance == null) {
                    instance = new QuestionBUS();
                }
            }
        }
        return instance;
    }
    public int getLastInsertID() {
        return QuestionDAO.getInstance().getLastInsertID();
    }


    // Thêm câu hỏi
    public boolean addQuestion(QuestionDTO question) {
        int result = questionDAO.add(question);
        return result > 0;
    }

    // Cập nhật câu hỏi
    public boolean updateQuestion(QuestionDTO question) {
        if (question == null || question.getQID() <= 0) {
            System.out.println("⚠️ Lỗi: ID câu hỏi không hợp lệ!");
            return false;
        }

        boolean success = QuestionDAO.getInstance().update(question) > 0;
        if (success) {
            System.out.println("✅ Cập nhật câu hỏi thành công!");
        } else {
            System.out.println("❌ Cập nhật thất bại!");
        }
        return success;
    }

    public boolean deleteQuestion(int questionID) {
        if (questionID <= 0) {
            System.out.println("⚠️ Lỗi: ID câu hỏi không hợp lệ!");
            return false;
        }

        boolean success = QuestionDAO.getInstance().delete(questionID) > 0;
        if (success) {
            System.out.println("✅ Xóa câu hỏi thành công!");
        } else {
            System.out.println("❌ Xóa thất bại!");
        }
        return success;
    }
    public boolean setQuestionStatus(int questionID, int status) {
        return QuestionDAO.getInstance().updateQuestionStatus(questionID, status);
    }

    public boolean updateQuestionImage(int questionID, String imagePath) {
        return QuestionDAO.getInstance().updateImage(questionID, imagePath);
    }

    // Lấy câu hỏi theo ID
    public QuestionDTO getQuestionById(int questionID) {
        QuestionDTO question = questionDAO.getQuestionById(questionID);
        return question;
    }

    public QuestionDTO getQuestionByContent(String content) {
        QuestionDTO question = questionDAO.getQuestionByContent(content);
        return question;
    }



    // Lấy danh sách tất cả câu hỏi
    public List<QuestionDTO> getAllQuestions() {
        List<QuestionDTO> questions = questionDAO.getAllQuestions();
        return questions;
    }

    public List<QuestionDTO> getQuestionsForTopic(int topicID) {
        List<QuestionDTO> questions = questionDAO.getQuestionsForTopic(topicID);
        return questions;
    }

    // Hàm chọn ngẫu nhiên câu hỏi từ danh sách
    public List<QuestionDTO> selectRandomQuestions(List<QuestionDTO> questions, String difficulty, int count) {
        List<QuestionDTO> filteredQuestions = new ArrayList<>();
        for (QuestionDTO question : questions) {
            if (question.getQLevel().equals(difficulty)) {
                filteredQuestions.add(question);
            }
        }

        // Xáo trộn danh sách câu hỏi
        Collections.shuffle(filteredQuestions, new Random());

        return filteredQuestions.subList(0, Math.min(count, filteredQuestions.size()));
    }

    public List<QuestionDTO> searchQuestions(String keyword) {
        return QuestionDAO.getInstance().searchQuestions(keyword);
    }
}
