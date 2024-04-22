package ch.richozm.youquizplay.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TR_User_Quiz")
public class UserQuiz {
    @Column(name = "QuizLike")
    private boolean like;

    @Column(name = "NbrPoints")
    private Integer points;

    @Id
    @Column(name = "PFK_Quiz")
    private Integer quiz;

    @Column(name = "PFK_User")
    private Integer user;

    public Boolean getLike() {
        return like;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getQuiz() {
        return quiz;
    }

    public Integer getUser() {
        return user;
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

    public void setUser(Integer user) {
        this.user = user;
    }
}
