package DTO;


import jakarta.persistence.*;

@Entity
@Table(name = "Answer")
@Access(AccessType.FIELD)
public class AnswerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerID;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String answerName;

    private int isCorrect;
    private int status;

    @ManyToOne
    @JoinColumn(name = "questionID")
    private QuestionDTO question;

    public AnswerDTO() {}

    public AnswerDTO(int answerID, String image, String answerName, int isCorrect, int status, QuestionDTO question) {
        this.answerID = answerID;
        this.image = image;
        this.answerName = answerName;
        this.isCorrect = isCorrect;
        this.status = status;
        this.question = question;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }
}
