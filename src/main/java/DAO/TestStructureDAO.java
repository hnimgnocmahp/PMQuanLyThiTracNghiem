package DAO;

import DTO.TestDTO;
import DTO.TestStructureDTO;
import DTO.TopicDTO;
import DTO.UserDTO;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestStructureDAO {
    public int add(TestStructureDTO testStructure) {
        int ketQua = 0;
        String sql = "INSERT INTO test_structure (testCode, tpID, num_easy, num_medium, num_diff) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, testStructure.getTestCode());
            preparedStatement.setInt(2, testStructure.getTpID());
            preparedStatement.setInt(3, testStructure.getNum_easy());
            preparedStatement.setInt(4, testStructure.getNum_medium());
            preparedStatement.setInt(5, testStructure.getNum_diff());

            ketQua = preparedStatement.executeUpdate();
            JDBCUtil.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public List<TestStructureDTO> selectAll() {
        List<TestStructureDTO> testStructures = new ArrayList<TestStructureDTO>();
        String sql = "SELECT * FROM test_structure";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                TestStructureDTO test_structure = new TestStructureDTO();
                test_structure.setTestCode(resultSet.getString("testCode"));
                test_structure.setTpID(resultSet.getInt("tpID"));
                test_structure.setNum_easy(resultSet.getInt("num_easy"));
                test_structure.setNum_medium(resultSet.getInt("num_medium"));
                test_structure.setNum_diff(resultSet.getInt("num_diff"));


                testStructures.add(test_structure);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testStructures;
    }

    public List<TestStructureDTO> getStructureByTestCode(String testCode){
        ArrayList<TestStructureDTO> structureDTOS = new ArrayList<>();
        String sql = "SELECT * FROM test_structure WHERE testCode = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,testCode);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TestStructureDTO testStructureDTO = new TestStructureDTO();

                testStructureDTO.setTestCode(rs.getString("testCode"));
                testStructureDTO.setTpID(rs.getInt("tpID"));
                testStructureDTO.setNum_easy(rs.getInt("num_easy"));
                testStructureDTO.setNum_medium(rs.getInt("num_medium"));
                testStructureDTO.setNum_diff(rs.getInt("num_diff"));

                structureDTOS.add(testStructureDTO);
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return structureDTOS;
    }
}

