package DTO;

import java.time.LocalDateTime;

public class LogDTO {
    private int logID;
    private String logContent;
    private int logUserID;
    private String logExCode;
    private LocalDateTime logDate;

    public LogDTO() {
    }

    public LogDTO(int logID, String logContent, int logUserID, String logExCode, LocalDateTime logDate) {
        this.logID = logID;
        this.logContent = logContent;
        this.logUserID = logUserID;
        this.logExCode = logExCode;
        this.logDate = logDate;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public int getLogUserID() {
        return logUserID;
    }

    public void setLogUserID(int logUserID) {
        this.logUserID = logUserID;
    }

    public String getLogExCode() {
        return logExCode;
    }

    public void setLogExCode(String logExCode) {
        this.logExCode = logExCode;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }
}

