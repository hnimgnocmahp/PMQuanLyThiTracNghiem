package DTO;

import java.sql.Date;

public class TestDTO {
    private int testID;
    private String testCode;
    private String testTitle;
    private int testTime;
    private int tpID;
    private int num_ease;
    private int num_medium;
    private int num_diff;
    private int testLimit;
    private Date testDate;
    private int testStatus;

    public TestDTO() {
    }

    public TestDTO(String testCode, String testTitle, int testTime, int tpID, int num_ease, int num_medium, int num_diff, int testLimit, Date testDate, int testStatus) {
        this.testCode = testCode;
        this.testTitle = testTitle;
        this.testTime = testTime;
        this.tpID = tpID;
        this.num_ease = num_ease;
        this.num_medium = num_medium;
        this.num_diff = num_diff;
        this.testLimit = testLimit;
        this.testDate = testDate;
        this.testStatus = testStatus;
    }

    public TestDTO(int testID, String testCode, String testTitle, int testTime, int tpID, int num_ease, int num_medium, int num_diff, int testLimit, Date testDate, int testStatus) {
        this.testID = testID;
        this.testCode = testCode;
        this.testTitle = testTitle;
        this.testTime = testTime;
        this.tpID = tpID;
        this.num_ease = num_ease;
        this.num_medium = num_medium;
        this.num_diff = num_diff;
        this.testLimit = testLimit;
        this.testDate = testDate;
        this.testStatus = testStatus;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public int getTpID() {
        return tpID;
    }

    public void setTpID(int tpID) {
        this.tpID = tpID;
    }

    public int getNum_ease() {
        return num_ease;
    }

    public void setNum_ease(int num_ease) {
        this.num_ease = num_ease;
    }

    public int getNum_medium() {
        return num_medium;
    }

    public void setNum_medium(int num_medium) {
        this.num_medium = num_medium;
    }

    public int getNum_diff() {
        return num_diff;
    }

    public void setNum_diff(int num_diff) {
        this.num_diff = num_diff;
    }

    public int getTestLimit() {
        return testLimit;
    }

    public void setTestLimit(int testLimit) {
        this.testLimit = testLimit;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public int getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(int testStatus) {
        this.testStatus = testStatus;
    }
}
