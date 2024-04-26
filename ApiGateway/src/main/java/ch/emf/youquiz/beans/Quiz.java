package ch.emf.youquiz.beans;

import java.util.List;

public class Quiz {

    private Integer pkQuiz;
    private String nom;
    private String description;
    private String username;
    private Integer likes = 0;
    private List<Question> questions;

    // Constructors
    public Quiz() {
        
    }

    public Quiz(Integer pkQuiz, String nom, String description, String username, List<Question> questions) {
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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
