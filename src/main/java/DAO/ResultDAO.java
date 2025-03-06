package DAO;

import DTO.ResultDTO;
import DTO.UserDTO;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ResultDAO {
    public int add(ResultDTO result) {
        int ketQua = 0;
        String sql = "INSERT INTO result (rs_num, userID, exCode, rs_anwsers, rs_mark, rs_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, result.getRs_num());
            preparedStatement.setInt(2, result.getUserID());
            preparedStatement.setString(3, result.getExCode());
            preparedStatement.setString(4, result.getRs_answers());
            preparedStatement.setBigDecimal(5, result.getRs_mark());
            preparedStatement.setDate(6, Date.valueOf(result.getRs_date()));

            ketQua = preparedStatement.executeUpdate();
            JDBCUtil.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }


}
