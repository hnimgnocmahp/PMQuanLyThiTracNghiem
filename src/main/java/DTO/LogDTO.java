package DTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "[Log]")
@Access(AccessType.FIELD)
public class LogDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logID;

    private String action;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "userID")
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "testCodeID")
    private TestCodeDTO testCode;

    public LogDTO() {
    }

    public LogDTO(int logID, String action, LocalDateTime date, UserDTO user, TestCodeDTO testCode) {
        this.logID = logID;
        this.action = action;
        this.date = date;
        this.user = user;
        this.testCode = testCode;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

