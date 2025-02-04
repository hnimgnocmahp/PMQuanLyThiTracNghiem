package DTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Result")
@Access(AccessType.FIELD)
public class ResultDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resultID;

    @Column(columnDefinition = "TEXT")
    private String answerList;

    private int score;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "userID")
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "testCodeID")
    private TestCodeDTO testCode;

    public ResultDTO() {
    }

    public ResultDTO(int resultID, String answerList, int score, LocalDateTime date, UserDTO user, TestCodeDTO testCode) {
        this.resultID = resultID;
        this.answerList = answerList;
        this.score = score;
        this.date = date;
        this.user = user;
        this.testCode = testCode;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public String getAnswerList() {
        return answerList;
    }

    public void setAnswerList(String answerList) {
        this.answerList = answerList;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public TestCodeDTO getTestCode() {
        return testCode;
    }

    public void setTestCode(TestCodeDTO testCode) {
        this.testCode = testCode;
    }
}
