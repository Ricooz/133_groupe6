package ch.morisettid.youquizcreation.dto;

import java.util.List;

public class QuizDTO {

    private Integer pkQuiz;
    private String nom;
    private String description;
    private List<QuestionDTO> questions;

    // Constructor
    public QuizDTO(Integer pkQuiz, String nom, String description, List<QuestionDTO> questions) {
        this.pkQuiz = pkQuiz;
        this.nom = nom;
        this.description = description;
        this.questions = questions;
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

    public List<QuestionDTO> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
