package DAO;

import DTO.TestCodeDTO;
import util.JDBCUtil;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestCodeDAO {
    public String generateNewID(Connection conn) throws SQLException {
        String query = "SELECT COALESCE(MAX(CAST(SUBSTRING(testCode, 3, LEN(testCode) - 2) AS INT)), 0) FROM exams";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int lastId = rs.getInt(1);
                return "TC" + (lastId + 1);
            }
        }
        return "TC1";
    }


    public int add(TestCodeDTO testCodeDTO) {
        int ketQua = 0;
        String sql = "INSERT INTO exams (testCode, exOrder, exCode, ex_quesIDs) VALUES (?, ?, ?, ?)";


        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (testCodeDTO.getTestCodeID() == null) {
                String newId = generateNewID(connection);
                testCodeDTO.setTestCodeID(newId);
            }

            preparedStatement.setString(1, testCodeDTO.getTestCodeID());
            preparedStatement.setString(2, testCodeDTO.getExOrder());
            preparedStatement.setString(3, testCodeDTO.getExCode());
            preparedStatement.setString(4, testCodeDTO.getEx_questionIDs());

            ketQua = preparedStatement.executeUpdate();
            JDBCUtil.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(TestCodeDTO testCodeDTO) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE exams SET exOrder=?, exCode=?, ex_quesIDs=? WHERE testCode=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);


            preparedStatement.setString(1, testCodeDTO.getExOrder());
            preparedStatement.setString(2, testCodeDTO.getExCode());
            preparedStatement.setString(3, testCodeDTO.getEx_questionIDs());
            preparedStatement.setString(4, testCodeDTO.getTestCodeID());

            ketQua = preparedStatement.executeUpdate();

            JDBCUtil.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

    public int delete(String testcodeID) {
        int ketqua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM exams WHERE testCode=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, testcodeID);

            ketqua = preparedStatement.executeUpdate();

            JDBCUtil.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketqua;
    }
}
