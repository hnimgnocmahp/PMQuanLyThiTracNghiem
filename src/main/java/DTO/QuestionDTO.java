package DTO;

import jakarta.persistence.*;

@Entity
@Table(name = "Question")
@Access(AccessType.FIELD)
public class QuestionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionID;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String image;
    private int level;
    private int status;

    @ManyToOne
    @JoinColumn(name = "topicID")
    private TopicDTO topic;

    public QuestionDTO() {}

    public QuestionDTO(int questionID, String content, String image, int level, int status, TopicDTO topic) {
        this.questionID = questionID;
        this.content = content;
        this.image = image;
        this.level = level;
        this.status = status;
        this.topic = topic;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TopicDTO getTopic() {
        return topic;
    }

    public void setTopic(TopicDTO topic) {
        this.topic = topic;
    }
}
