package ch.morisettid.youquizcreation.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.tomcat.util.json.JSONParser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_Quiz")
public class Quiz {

    // Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_Quiz")
    private Integer pkQuiz;

    @Column(name = "Nom", length = 50)
    private String nom;

    @Column(name = "Description", length = 250)
    private String description;

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    // Getters et Setters
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

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}