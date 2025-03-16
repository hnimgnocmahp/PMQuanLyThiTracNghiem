package DTO;

import java.math.BigDecimal;

public class Statistics {
    private int rs_num;
    private String testTitle;
    private String exCode;
    private String rs_answer;
    private BigDecimal rs_mark;
    private java.time.LocalDate rs_date;

    public Statistics(ResultDTO result, String testTitle) {
        this.rs_num = result.getRs_num();
        this.exCode = result.getExCode();
        this.rs_answer = result.getRs_answers();
        this.rs_mark = result.getRs_mark();
        // Chuyển từ LocalDateTime sang LocalDate
        this.rs_date = result.getRs_date();
        this.testTitle = testTitle;
    }

    public int getRs_num() {
        return rs_num;
    }
    public String getTestTitle() {
        return testTitle;
    }
    public String getExCode() {
        return exCode;
    }
    public String getRs_answer() {
        return rs_answer;
    }
    public BigDecimal getRs_mark() {
        return rs_mark;
    }
    public java.time.LocalDate getRs_date() {
        return rs_date;
    }
}
