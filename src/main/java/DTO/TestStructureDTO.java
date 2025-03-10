package DTO;

public class TestStructureDTO {
    private String testCode;
    private int tpID;
    private int num_easy;
    private int num_medium;
    private int num_diff;

    public TestStructureDTO() {
    }

    public TestStructureDTO(String testCode, int tpID, int num_easy, int num_medium, int num_diff) {
        this.testCode = testCode;
        this.tpID = tpID;
        this.num_easy = num_easy;
        this.num_medium = num_medium;
        this.num_diff = num_diff;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public int getTpID() {
        return tpID;
    }

    public void setTpID(int tpID) {
        this.tpID = tpID;
    }

    public int getNum_easy() {
        return num_easy;
    }

    public void setNum_easy(int num_easy) {
        this.num_easy = num_easy;
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
}
