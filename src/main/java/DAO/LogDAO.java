package DAO;

import DTO.LogDTO;
import util.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static util.JDBCUtil.getConnection;

public class LogDAO {
    public boolean insertLog(LogDTO log) {
    String sql = "INSERT INTO logs (logContent, logUserID, logExCode, logDate) VALUES (?, ?, ?, ?)";
    try (Connection conn = JDBCUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, log.getLogContent());
        ps.setInt(2, log.getLogUserID());
        ps.setString(3, log.getLogExCode());
        ps.setObject(4, log.getLogDate());

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return false;
}

    // Lấy danh sách logs theo userID
    public List<LogDTO> getLogsByUserID(int userID) {
        List<LogDTO> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs WHERE logUserID = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LogDTO log = new LogDTO();
                    log.setLogID(rs.getInt("logID"));
                    log.setLogContent(rs.getString("logContent"));
                    log.setLogUserID(rs.getInt("logUserID"));
                    log.setLogExCode(rs.getString("logExCode"));
                    log.setLogDate(rs.getObject("logDate", LocalDateTime.class));
                    logs.add(log);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return logs;
    }

    // Lấy danh sách tất cả logs
    public List<LogDTO> getAllLogs() {
        List<LogDTO> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LogDTO log = new LogDTO();
                log.setLogID(rs.getInt("logID"));
                log.setLogContent(rs.getString("logContent"));
                log.setLogUserID(rs.getInt("logUserID"));
                log.setLogExCode(rs.getString("logExCode"));
                log.setLogDate(rs.getObject("logDate", LocalDateTime.class));
                logs.add(log);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return logs;
    }
}
