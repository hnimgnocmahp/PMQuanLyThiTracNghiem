package DTO;

import java.sql.Date;

public class TestDTO {
    private int testID;
    private String testCode;
    private String testTitle;
    private int testTime;
    private int testLimit;
    private Date testDate;
    private int testStatus;

    public TestDTO() {
    }

    public TestDTO(String testCode, String testTitle, int testTime, int testLimit, Date testDate, int testStatus) {
        this.testCode = testCode;
        this.testTitle = testTitle;
        this.testTime = testTime;
        this.testLimit = testLimit;
        this.testDate = testDate;
        this.testStatus = testStatus;
    }

    public TestDTO(int testID, String testCode, String testTitle, int testTime, int testLimit, Date testDate, int testStatus) {
        this.testID = testID;
        this.testCode = testCode;
        this.testTitle = testTitle;
        this.testTime = testTime;
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
