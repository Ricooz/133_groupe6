package ch.morisettid.youquizcreation.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_Question")
public class Question {

    // Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_Question")
    private Integer pkQuestion;
    
    @Column(name = "Nom", length = 50)
    private String nom;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "FK_Quiz")
    private Quiz quiz;

    @OneToMany(mappedBy = "question")
    private List<Reponse> reponses;

    // Getters et Setters
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

    public Quiz getQuiz() {
        return this.quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Reponse> getReponses() {
        return this.reponses;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }
}