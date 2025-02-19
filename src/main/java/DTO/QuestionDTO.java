package DTO;

import javafx.beans.property.*;

public class QuestionDTO {
    private final IntegerProperty qID;
    private final StringProperty qContent;
    private final StringProperty qPictures;
    private final IntegerProperty qTopicID;
    private final StringProperty qLevel;
    private final IntegerProperty qStatus;

    public QuestionDTO(int qID, String qContent, String qPictures, int qTopicID, String qLevel, int qStatus) {
        this.qID = new SimpleIntegerProperty(qID);
        this.qContent = new SimpleStringProperty(qContent);
        this.qPictures = new SimpleStringProperty(qPictures);
        this.qTopicID = new SimpleIntegerProperty(qTopicID);
        this.qLevel = new SimpleStringProperty(qLevel);
        this.qStatus = new SimpleIntegerProperty(qStatus);
    }

    // ✅ Đổi tên getter cho khớp với BUS
    public int getQID() { return qID.get(); }
    public void setQID(int qID) { this.qID.set(qID); }

    public String getQContent() { return qContent.get(); }
    public void setQContent(String qContent) { this.qContent.set(qContent); }

    public String getQPictures() { return qPictures.get(); }
    public void setQPictures(String qPictures) { this.qPictures.set(qPictures); }

    public int getQTopicID() { return qTopicID.get(); }
    public void setQTopicID(int qTopicID) { this.qTopicID.set(qTopicID); }

    public String getQLevel() { return qLevel.get(); }
    public void setQLevel(String qLevel) { this.qLevel.set(qLevel); }

    public int getQStatus() { return qStatus.get(); }
    public void setQStatus(int qStatus) { this.qStatus.set(qStatus); }

    // ✅ Getter cho JavaFX TableView
    public IntegerProperty qIDProperty() { return qID; }
    public StringProperty qContentProperty() { return qContent; }
    public StringProperty qPicturesProperty() { return qPictures; }
    public IntegerProperty qTopicIDProperty() { return qTopicID; }
    public StringProperty qLevelProperty() { return qLevel; }
    public IntegerProperty qStatusProperty() { return qStatus; }
}
