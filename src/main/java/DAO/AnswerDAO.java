package DAO;

import DTO.AnswerDTO;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    private static AnswerDAO instance;

    private AnswerDAO() {}

    public static AnswerDAO getInstance() {
        if (instance == null) {
            synchronized (AnswerDAO.class) {
                if (instance == null) {
                    instance = new AnswerDAO();
                }
            }
        }
        return instance;
    }
    public boolean updateAnswerImage(int awID, String imagePath) {
        String query = "UPDATE answers SET awPictures = ? WHERE awID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, imagePath);
            stmt.setInt(2, awID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ✅ Thêm danh sách câu trả lời cho một câu hỏi
     */
    public void addAnswers(List<AnswerDTO> answers) {
        String sql = "INSERT INTO answers (qID, awContent, awPictures, isRight, awStatus) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (AnswerDTO answer : answers) {
                stmt.setInt(1, answer.getQID());
                stmt.setString(2, answer.getAwContent());
                stmt.setString(3, answer.getAwPictures());
                stmt.setInt(4, answer.getIsRight());
                stmt.setInt(5, answer.getAwStatus());
                stmt.addBatch();
            }
            stmt.executeBatch();
            System.out.println("Thêm danh sách câu trả lời thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean deleteAnswersByQuestionID(int questionID) {
        String sql = "DELETE FROM answers WHERE qID=?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, questionID);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int add(AnswerDTO answer) {
        int result = 0;
        String sql = "INSERT INTO answers (qID, awContent, awPictures, isRight, awStatus) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, answer.getQID());
            stmt.setString(2, answer.getAwContent());
            stmt.setString(3, answer.getAwPictures() != null ? answer.getAwPictures() : "");
            stmt.setInt(4, answer.getIsRight());
            stmt.setInt(5, answer.getAwStatus());

            result = stmt.executeUpdate();

            // Lấy ID của câu trả lời vừa thêm
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    answer.setAwID(generatedKeys.getInt(1));
                }
            }

            System.out.println("Thêm câu trả lời vào DB thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ✅ Cập nhật thông tin câu trả lời
     */
    public boolean updateAnswer(AnswerDTO answer) {
        String sql = "UPDATE answers SET awContent=?, awPictures=?, isRight=?, awStatus=? WHERE awID=?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, answer.getAwContent());
            stmt.setString(2, answer.getAwPictures());
            stmt.setInt(3, answer.getIsRight());
            stmt.setInt(4, answer.getAwStatus());
            stmt.setInt(5, answer.getAwID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * ✅ Xóa câu trả lời theo `awID`
     */
    public boolean deleteAnswer(int awID) {
        String sql = "DELETE FROM answers WHERE awID=?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, awID);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateAnswersStatusByQuestionID(int questionID, int status) {
        String sql = "UPDATE answers SET awStatus=? WHERE qID=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, status);
            stmt.setInt(2, questionID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ✅ Lấy danh sách câu trả lời theo `qID`
     */
    public List<AnswerDTO> getAnswersByQuestionID(int questionID) {
        List<AnswerDTO> answers = new ArrayList<>();
        String sql = "SELECT * FROM answers WHERE qID = ? AND awStatus = 1"; // Chỉ lấy câu trả lời chưa bị ẩn

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, questionID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AnswerDTO answer = new AnswerDTO(
                        rs.getInt("awID"),
                        rs.getInt("qID"),
                        rs.getString("awContent"),
                        rs.getString("awPictures"),
                        rs.getInt("isRight"),
                        rs.getInt("awStatus")
                );
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answers;
    }


}
