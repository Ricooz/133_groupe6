package ch.richozm.youquizplay.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TR_User_Quiz")
public class UserQuiz {
    @Column(name = "Like")
    private boolean like;

    @Column(name = "NbrPoints")
    private Integer points;

    @Id
    @Column(name = "PFK_Quiz")
    private Integer quiz;

    public Boolean getLike() {
        return like;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getQuiz() {
        return quiz;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public void setPoints(Integer points) {
       this.points = points;
    }

    public void setQuiz(Integer quiz) {
        this.quiz = quiz;
    }
}
