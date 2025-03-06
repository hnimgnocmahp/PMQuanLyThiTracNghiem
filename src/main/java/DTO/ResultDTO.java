package DTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ResultDTO {
    private int rs_num;
    private int userID;
    private String exCode;
    private String rs_answers;
    private BigDecimal rs_mark;
    private LocalDate rs_date;

    public ResultDTO() {
    }

    public ResultDTO(int rs_num, int userID, String exCode, String rs_answers, BigDecimal rs_mark, LocalDate rs_date) {
        this.rs_num = rs_num;
        this.userID = userID;
        this.exCode = exCode;
        this.rs_answers = rs_answers;
        this.rs_mark = rs_mark;
        this.rs_date = rs_date;
    }

    public int getRs_num() {
        return rs_num;
    }

    public void setRs_num(int rs_num) {
        this.rs_num = rs_num;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getRs_answers() {
        return rs_answers;
    }

    public void setRs_answers(String rs_answers) {
        this.rs_answers = rs_answers;
    }

    public BigDecimal getRs_mark() {
        return rs_mark;
    }

    public void setRs_mark(BigDecimal rs_mark) {
        this.rs_mark = rs_mark;
    }

    public LocalDate getRs_date() {
        return rs_date;
    }

    public void setRs_date(LocalDate rs_date) {
        this.rs_date = rs_date;
    }
}
