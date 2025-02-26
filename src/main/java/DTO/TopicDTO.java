package DTO;


public class TopicDTO {
    private int topicID;
    private String topicTitle;
    private int topicParent;
    private int topicStatus;

    public TopicDTO() {
    }

    public TopicDTO(String topicTitle, int topicParent, int topicStatus) {
        this.topicTitle = topicTitle;
        this.topicParent = topicParent;
        this.topicStatus = topicStatus;
    }


    public TopicDTO(int topicID, String topicTitle, int topicParent, int topicStatus) {
        this.topicID = topicID;
        this.topicTitle = topicTitle;
        this.topicParent = topicParent;
        this.topicStatus = topicStatus;
    }

    @Override
    public String toString() {
        return topicTitle; // 🔥 Chỉ hiển thị tên chủ đề, không hiển thị ID!
    }



    public int getTopicID() {
        return topicID;  // Đảm bảo topicID không bị set = 0

    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public int getTopicParent() {
        return topicParent;
    }

    public void setTopicParent(int topicParent) {
        this.topicParent = topicParent;
    }

    public int getTopicStatus() {
        return topicStatus;
    }

    public void setTopicStatus(int topicStatus) {
        this.topicStatus = topicStatus;
    }
}
