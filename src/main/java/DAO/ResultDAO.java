package DAO;

import DTO.ResultDTO;
import DTO.UserDTO;
import util.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public int getLastrs_numByUserIdAndExcCode(int userID, String exCode){
        int ketQua = 0;
        String sql = "Select rs_num from result WHERE userID = ? and exCode = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2,exCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if (ketQua < resultSet.getInt("rs_num")){
                    ketQua = resultSet.getInt("rs_num");
                }
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public List<ResultDTO> getResultOfUser(int userID){
        ArrayList<ResultDTO> list = new ArrayList<>();
        String sql = "Select * from result WHERE userID = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement rs = connection.prepareStatement(sql);
            rs.setInt(1,userID);
            ResultSet resultSet = rs.executeQuery();
            while(resultSet.next()){
                ResultDTO result = new ResultDTO();
                result.setUserID(resultSet.getInt("userID"));
                result.setExCode(resultSet.getString("exCode"));
                result.setRs_answers(resultSet.getString("rs_anwsers"));
                result.setRs_mark(resultSet.getBigDecimal("rs_mark"));
                result.setRs_date(resultSet.getDate("rs_date").toLocalDate());
                result.setRs_num(resultSet.getInt("rs_num"));
                list.add(result);
            }
            JDBCUtil.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    }

