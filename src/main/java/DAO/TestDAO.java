package DAO;

import DTO.TestDTO;
import DTO.UserDTO;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {

    public int add(TestDTO test) {
        int ketQua = 0;
        String sql = "INSERT INTO test (testID, testCode, testTilte, testTime, tpID, num_easy, num_medium, num_diff, testLimit, testDate, testStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, test.getTestID());
            preparedStatement.setString(2, test.getTestCode());
            preparedStatement.setString(3, test.getTestTitle());
            preparedStatement.setInt(4, test.getTestTime());
            preparedStatement.setInt(5, test.getTpID());
            preparedStatement.setInt(6, test.getNum_ease());
            preparedStatement.setInt(7, test.getNum_medium());
            preparedStatement.setInt(8, test.getNum_diff());
            preparedStatement.setInt(9, test.getTestLimit());
            preparedStatement.setDate(10, test.getTestDate());
            preparedStatement.setInt(11, test.getTestStatus());

            ketQua = preparedStatement.executeUpdate();
            JDBCUtil.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(TestDTO test) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE test SET testLimit=? WHERE testID=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, test.getTestLimit());
            preparedStatement.setInt(2, test.getTestID());

            ketQua = preparedStatement.executeUpdate();

            JDBCUtil.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ketQua;
    }

//    public int delete(int testID) {
//        int ketqua = 0;
//        try {
//            Connection con = JDBCUtil.getConnection();
//            String sql = "DELETE FROM test WHERE testID=?";
//            PreparedStatement preparedStatement = con.prepareStatement(sql);
//
//            preparedStatement.setInt(1, testID);
//
//            ketqua = preparedStatement.executeUpdate();
//
//            JDBCUtil.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return ketqua;
//    }

    public List<TestDTO> selectAll() {
        List<TestDTO> tests = new ArrayList<TestDTO>();
        String sql = "SELECT * FROM test";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                TestDTO test = new TestDTO();
                test.setTestID(resultSet.getInt("testID"));
                test.setTestCode(resultSet.getString("testCode"));
                test.setTestTitle(resultSet.getString("testTilte"));
                test.setTestTime(resultSet.getInt("testTime"));
                test.setTpID(resultSet.getInt("tpID"));
                test.setNum_ease(resultSet.getInt("num_easy"));
                test.setNum_medium(resultSet.getInt("num_medium"));
                test.setNum_diff(resultSet.getInt("num_diff"));
                test.setTestLimit(resultSet.getInt("testLimit"));
                test.setTestDate(resultSet.getDate("testDate"));
                test.setTestStatus(resultSet.getInt("testStatus"));

                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }

    public TestDTO getTestByID(String testCode) {
        String sql = "SELECT testID, testCode, testTilte, testTime, tpID, num_easy, num_medium, num_diff, testLimit, testDate, testStatus FROM test WHERE testCode = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, testCode);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new TestDTO(
                            resultSet.getInt("testID"),
                            resultSet.getString("testCode"),
                            resultSet.getString("testTilte"),
                            resultSet.getInt("testTime"),
                            resultSet.getInt("tpID"),
                            resultSet.getInt("num_easy"),
                            resultSet.getInt("num_medium"),
                            resultSet.getInt("num_diff"),
                            resultSet.getInt("testLimit"),
                            resultSet.getDate("testDate"),
                            resultSet.getInt("testStatus")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin User: " + e.getMessage());
        }
        return null; // Trả về null nếu không tìm thấy
    }
}
