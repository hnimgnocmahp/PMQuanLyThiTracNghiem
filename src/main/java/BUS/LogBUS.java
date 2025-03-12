package BUS;

import DAO.LogDAO;
import DTO.LogDTO;

import java.time.LocalDateTime;
import java.util.List;

public class LogBUS {
    private LogDAO logDAO;

    public LogBUS() {
        logDAO = new LogDAO();
    }

    // Thêm mới log; tự động gán thời gian hiện tại cho logDate
    public boolean addLog(String logContent, int logUserID, String logExCode) {
        LocalDateTime currentTime = LocalDateTime.now();
        LogDTO log = new LogDTO();
        log.setLogContent(logContent);
        log.setLogUserID(logUserID);
        log.setLogExCode(logExCode);
        log.setLogDate(currentTime);

        return logDAO.insertLog(log);
    }

    // Lấy danh sách logs theo userID
    public List<LogDTO> getLogsByUserID(int userID) {
        return logDAO.getLogsByUserID(userID);
    }

    // Lấy danh sách tất cả logs
    public List<LogDTO> getAllLogs() {
        return logDAO.getAllLogs();
    }
}
