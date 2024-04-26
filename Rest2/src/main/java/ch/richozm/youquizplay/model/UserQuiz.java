package ch.richozm.youquizplay.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tr_user_quiz")
public class UserQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Integer id; 

    @Column(name = "quiz_like")
    private Boolean like;

    @Column(name = "nbr_points")
    private Integer points;

    @Column(name = "quizId")
    private Integer quizId;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User fkUser;

    public Integer getId() {
        return id;
    }

    public Boolean getLike() {
        return like;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getQuiz() {
        return quizId;
    }

    public User getUser() {
        return fkUser;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public void setPoints(Integer points) {
       this.points = points;
    }

    public void setQuiz(Integer quizId) {
        this.quizId = quizId;
    }

    public void setUser( User fkUser) {
        this.fkUser = fkUser;
    }
}
