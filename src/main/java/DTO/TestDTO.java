package DTO;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Test")
@Access(AccessType.FIELD)
public class TestDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int testID;

    private String name;
    private int ease;
    private int medium;
    private int hard;
    private int time;

    @Column(nullable = false)
    private int numberOfTimes;

    private LocalDateTime date;
    private int status;

    @ManyToOne
    @JoinColumn(name = "topicID")
    private TopicDTO topic;

    public TestDTO() {
    }

    public TestDTO(int testID, String name, int ease, int medium, int hard, int time, int numberOfTimes, LocalDateTime date, int status) {
        this.testID = testID;
        this.name = name;
        this.ease = ease;
        this.medium = medium;
        this.hard = hard;
        this.time = time;
        this.numberOfTimes = numberOfTimes;
        this.date = date;
        this.status = status;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEase() {
        return ease;
    }

    public void setEase(int ease) {
        this.ease = ease;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getHard() {
        return hard;
    }

    public void setHard(int hard) {
        this.hard = hard;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNumberOfTimes() {
        return numberOfTimes;
    }

    public void setNumberOfTimes(int numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
