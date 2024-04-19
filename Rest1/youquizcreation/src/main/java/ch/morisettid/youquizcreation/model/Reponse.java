package ch.morisettid.youquizcreation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_Reponse")
public class Reponse {

    // Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_Reponse")
    private Integer pkReponse;
    
    @Column(name = "Nom", length = 50)
    private String nom;

    @Column(name = "Correct")
    private Boolean correct;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "FK_Question")
    private Question question;

    // Getters et Setters
    public Integer getPkReponse() {
        return pkReponse;
    }

    public void setPkQReponse(Integer pkReponse) {
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}