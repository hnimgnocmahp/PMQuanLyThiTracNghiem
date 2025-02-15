package DTO;


public class QuestionDTO {
    private int questionID;
    private String qContent;
    private String qPictures;
    private int qTopicID;
    private String qLevel;
    private int qStatus;


    public QuestionDTO() {}

    public QuestionDTO(int questionID, String qContent, String qPictures, int qTopicID, String qLevel, int qStatus) {
        this.questionID = questionID;
        this.qContent = qContent;
        this.qPictures = qPictures;
        this.qTopicID = qTopicID;
        this.qLevel = qLevel;
        this.qStatus = qStatus;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getqContent() {
        return qContent;
    }

    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    public String getqPictures() {
        return qPictures;
    }

    public void setqPictures(String qPictures) {
        this.qPictures = qPictures;
    }

    public int getqTopicID() {
        return qTopicID;
    }

    public void setqTopicID(int qTopicID) {
        this.qTopicID = qTopicID;
    }

    public String getqLevel() {
        return qLevel;
    }

    public void setqLevel(String qLevel) {
        this.qLevel = qLevel;
    }

    public int getqStatus() {
        return qStatus;
    }

    public void setqStatus(int qStatus) {
        this.qStatus = qStatus;
    }
}
