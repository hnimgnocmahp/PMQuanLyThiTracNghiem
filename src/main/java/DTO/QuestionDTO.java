package DTO;

import javafx.beans.property.*;

public class QuestionDTO {
    private int qID;
    private String qContent;
    private String qPictures;
    private int qTopicID;
    private String qLevel;
    private int qStatus;

    public QuestionDTO() {
    }

    public QuestionDTO(int qID, String qContent, String qPictures, int qTopicID, String qLevel, int qStatus) {
        this.qID = qID;
        this.qContent = qContent;
        this.qPictures = qPictures;
        this.qTopicID = qTopicID;
        this.qLevel = qLevel;
        this.qStatus = qStatus;
    }

    public int getQID() {
        return qID;
    }

    public void setQID(int qID) {
        this.qID = qID;
    }

    public String getQContent() {
        return qContent;
    }

    public void setQContent(String qContent) {
        this.qContent = qContent;
    }

    public String getQPictures() {
        return qPictures;
    }

    public void setQPictures(String qPictures) {
        this.qPictures = qPictures;
    }

    public int getQTopicID() {
        return qTopicID;
    }

    public void setQTopicID(int qTopicID) {
        this.qTopicID = qTopicID;
    }

    public String getQLevel() {
        return qLevel;
    }

    public void setQLevel(String qLevel) {
        this.qLevel = qLevel;
    }

    public int getQStatus() {
        return qStatus;
    }

    public void setQStatus(int qStatus) {
        this.qStatus = qStatus;
    }

    //    // ✅ Getter cho JavaFX TableView
//    public IntegerProperty qIDProperty() { return qID; }
//    public StringProperty qContentProperty() { return qContent; }
//    public StringProperty qPicturesProperty() { return qPictures; }
//    public IntegerProperty qTopicIDProperty() { return qTopicID; }
//    public StringProperty qLevelProperty() { return qLevel; }
//    public IntegerProperty qStatusProperty() { return qStatus; }
}
