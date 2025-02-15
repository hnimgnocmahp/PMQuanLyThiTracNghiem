package DTO;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ResultDTO {
    private int re_num;
    private int userID;
    private String exCode;
    private String rs_answer;
    private BigDecimal rs_mark;
    private LocalDateTime rs_date;

    public ResultDTO() {
    }

    public ResultDTO(int re_num, int userID, String exCode, String rs_answer, BigDecimal rs_mark, LocalDateTime rs_date) {
        this.re_num = re_num;
        this.userID = userID;
        this.exCode = exCode;
        this.rs_answer = rs_answer;
        this.rs_mark = rs_mark;
        this.rs_date = rs_date;
    }

    public int getRe_num() {
        return re_num;
    }

    public void setRe_num(int re_num) {
        this.re_num = re_num;
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

    public String getRs_answer() {
        return rs_answer;
    }

    public void setRs_answer(String rs_answer) {
        this.rs_answer = rs_answer;
    }

    public BigDecimal getRs_mark() {
        return rs_mark;
    }

    public void setRs_mark(BigDecimal rs_mark) {
        this.rs_mark = rs_mark;
    }

    public LocalDateTime getRs_date() {
        return rs_date;
    }

    public void setRs_date(LocalDateTime rs_date) {
        this.rs_date = rs_date;
    }
}
