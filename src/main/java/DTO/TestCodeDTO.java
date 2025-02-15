package DTO;



public class TestCodeDTO {
    private String testCodeID;
    private String exOrder;
    private String exCode;
    private String ex_questionIDs;

    public TestCodeDTO() {
    }

    public TestCodeDTO(String testCodeID, String exOrder, String exCode, String ex_questionIDs) {
        this.testCodeID = testCodeID;
        this.exOrder = exOrder;
        this.exCode = exCode;
        this.ex_questionIDs = ex_questionIDs;
    }

    public String getTestCodeID() {
        return testCodeID;
    }

    public void setTestCodeID(String testCodeID) {
        this.testCodeID = testCodeID;
    }

    public String getExOrder() {
        return exOrder;
    }

    public void setExOrder(String exOrder) {
        this.exOrder = exOrder;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getEx_questionIDs() {
        return ex_questionIDs;
    }

    public void setEx_questionIDs(String ex_questionIDs) {
        this.ex_questionIDs = ex_questionIDs;
    }
}
