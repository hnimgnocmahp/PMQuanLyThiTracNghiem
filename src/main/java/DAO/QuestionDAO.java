package DAO;

import DTO.QuestionDTO;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private static QuestionDAO instance;

    private QuestionDAO() {}

    public static QuestionDAO getInstance() {
        if (instance == null) {
            synchronized (QuestionDAO.class) {
                if (instance == null) {
                    instance = new QuestionDAO();
                }
            }
        }
        return instance;
    }
    public int add(QuestionDTO question) {
        int result = 0;
        String sql = "INSERT INTO questions (qContent, qPictures, qTopicID, qLevel, qStatus) VALUES (?, ?, ?, ?, ?)";


        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, question.getQContent());
            preparedStatement.setString(2, question.getQPictures());
            preparedStatement.setInt(3, question.getQTopicID());
            preparedStatement.setString(4, question.getQLevel());
            preparedStatement.setInt(5, question.getQStatus());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public int getLastInsertID() {
        int lastID = -1;
        String sql = "SELECT MAX(qID) FROM questions";
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                lastID = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastID;
    }


    public int update(QuestionDTO question) {
        int result = 0;
        String sql = "UPDATE questions SET qContent=?, qPictures=?, qTopicID=?, qLevel=?, qStatus=? WHERE qID=?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, question.getQContent());
            preparedStatement.setString(2, question.getQPictures());
            preparedStatement.setInt(3, question.getQTopicID());
            preparedStatement.setString(4, question.getQLevel());
            preparedStatement.setInt(5, question.getQStatus());
            preparedStatement.setInt(6, question.getQID());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public int delete(int questionID) {
        int result = 0;
        String sql = "DELETE FROM questions WHERE qID=?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, questionID);
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public QuestionDTO getQuestionById(int questionID) {
        QuestionDTO question = null;
        String sql = "SELECT * FROM questions WHERE qID=?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, questionID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                question = new QuestionDTO(
                        resultSet.getInt("qID"),
                        resultSet.getString("qContent"),
                        resultSet.getString("qPictures"),
                        resultSet.getInt("qTopicID"),
                        resultSet.getString("qLevel"),
                        resultSet.getInt("qStatus")
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return question;
    }

    public List<QuestionDTO> getAllQuestions() {
        List<QuestionDTO> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                QuestionDTO question = new QuestionDTO(
                        resultSet.getInt("qID"),
                        resultSet.getString("qContent"),
                        resultSet.getString("qPictures"),
                        resultSet.getInt("qTopicID"),
                        resultSet.getString("qLevel"),
                        resultSet.getInt("qStatus")
                );
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
