package ch.morisettid.youquizcreation.dto;

public class ReponseDTO {

    private Integer pkReponse;
    private String nom;
    private Boolean correct;

    public ReponseDTO(Integer pkReponse, String nom, Boolean correct) {
        this.pkReponse = pkReponse;
        this.nom = nom;
        this.correct = correct;
    }

    // Getters and setters...
    public Integer getPkReponse() {
        return pkReponse;
    }

    public void setPkReponse(Integer pkReponse) {
        this.pkReponse = pkReponse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean isCorrect() {
        return this.correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
