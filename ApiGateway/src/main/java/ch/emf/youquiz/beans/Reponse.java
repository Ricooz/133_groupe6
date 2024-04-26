package ch.emf.youquiz.beans;

public class Reponse {

    private Integer pkReponse;
    private String nom;
    private Boolean correct;

    public Reponse(Integer pkReponse, String nom, Boolean correct) {
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
