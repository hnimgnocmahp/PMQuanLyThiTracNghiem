package DTO;

public class AnswerDTO {
    private int answerID;
    private int questionID;
    private String answerContent;
    private String image;
    private int isRight;
    private int status;


    public AnswerDTO() {}

    public AnswerDTO(int answerID, int questionID, String answerContent, String image, int isRight, int status) {
        this.answerID = answerID;
        this.questionID = questionID;
        this.answerContent = answerContent;
        this.image = image;
        this.isRight = isRight;
        this.status = status;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsRight() {
        return isRight;
    }

    public void setIsRight(int isRight) {
        this.isRight = isRight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
