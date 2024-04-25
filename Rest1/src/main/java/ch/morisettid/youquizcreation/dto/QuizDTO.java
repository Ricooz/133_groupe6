package ch.morisettid.youquizcreation.dto;

import java.util.List;

public class QuizDTO {

    private Integer pkQuiz;
    private String nom;
    private String description;
    private String username;
    private List<QuestionDTO> questions;

    // Constructors
    public QuizDTO() {
        
    }

    public QuizDTO(Integer pkQuiz, String nom, String description, String username, List<QuestionDTO> questions) {
        this.pkQuiz = pkQuiz;
        this.nom = nom;
        this.description = description;
        this.questions = questions;
        this.username = username;
    }

    // Getters and setters...
    public Integer getPkQuiz() {
        return pkQuiz;
    }

    public void setPkQuiz(Integer pkQuiz) {
        this.pkQuiz = pkQuiz;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<QuestionDTO> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
