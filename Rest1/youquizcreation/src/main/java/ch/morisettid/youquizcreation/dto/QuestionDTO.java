package ch.morisettid.youquizcreation.dto;

import java.util.List;

public class QuestionDTO {

    private Integer pkQuestion;
    private String nom;
    private List<ReponseDTO> reponses;

    public QuestionDTO(Integer pkQuestion, String nom, List<ReponseDTO> reponses) {
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

    public List<ReponseDTO> getReponses() {
        return reponses;
    }

    public void setReponses(List<ReponseDTO> reponses) {
        this.reponses = reponses;
    }
}
