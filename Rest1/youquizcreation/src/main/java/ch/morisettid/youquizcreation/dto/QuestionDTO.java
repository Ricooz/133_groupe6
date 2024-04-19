package ch.morisettid.youquizcreation.dto;

import java.util.List;

public class QuestionDTO {

    private Integer pkQuestion;
    private String nom;
    private String quizName;
    private List<ReponseDTO> reponses;

    public QuestionDTO(Integer pkQuestion, String nom, String quizName, List<ReponseDTO> reponses) {
        this.pkQuestion = pkQuestion;
        this.nom = nom;
        this.quizName = quizName;
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

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public List<ReponseDTO> getReponses() {
        return reponses;
    }

    public void setReponses(List<ReponseDTO> reponses) {
        this.reponses = reponses;
    }
}
