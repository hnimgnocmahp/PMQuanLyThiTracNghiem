package DTO;
import jakarta.persistence.*;

@Entity
@Table(name = "TestCode")

public class TestCodeDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int testCodeID;

    @Column(columnDefinition = "TEXT")
    private String questionList;

    @ManyToOne
    @JoinColumn(name = "userID")
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "testID")
    private TestDTO test;

    public TestCodeDTO() {
    }

    public TestCodeDTO(int testCodeID, String questionList, UserDTO user, TestDTO test) {
        this.testCodeID = testCodeID;
        this.questionList = questionList;
        this.user = user;
        this.test = test;
    }

    public int getTestCodeID() {
        return testCodeID;
    }

    public void setTestCodeID(int testCodeID) {
        this.testCodeID = testCodeID;
    }

    public String getQuestionList() {
        return questionList;
    }

    public void setQuestionList(String questionList) {
        this.questionList = questionList;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public TestDTO getTest() {
        return test;
    }

    public void setTest(TestDTO test) {
        this.test = test;
    }
}
