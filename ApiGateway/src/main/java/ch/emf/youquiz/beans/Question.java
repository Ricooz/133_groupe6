package ch.emf.youquiz.beans;

import java.util.List;

public class Question {

    private Integer pkQuestion;
    private String nom;
    private List<Reponse> reponses;

    public Question(Integer pkQuestion, String nom, List<Reponse> reponses) {
        this.pkQuestion = pkQuestion;
        this.nom = nom;
        this.reponses = reponses;
    }

    // Getters and setters...
    public Integer getPkQuestion() {
        return pkQuestion;
    }

    public void setPkQuestion(Integer pkQuestion) {
        this.pkQuestion = pkQuestion;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }
}
