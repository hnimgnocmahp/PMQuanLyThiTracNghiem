package DTO;

import jakarta.persistence.*;

@Entity
@Table(name = "Topic")
public class TopicDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int topicID;

    @Column(name = "topicName")
    private String topicName;

    @Column(name = "status")
    private int status;

    public TopicDTO() {
    }

    public TopicDTO(int topicID, String topicName, int status) {
        this.topicID = topicID;
        this.topicName = topicName;
        this.status = status;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
