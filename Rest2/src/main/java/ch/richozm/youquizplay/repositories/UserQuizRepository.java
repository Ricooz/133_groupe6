package ch.richozm.youquizplay.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.richozm.youquizplay.model.User;
import ch.richozm.youquizplay.model.UserQuiz;

// This will be AUTO IMPLEMENTED by Spring into a Bean called SkieurRepository
// CRUD refers Create, Read, Update, Delete
public interface UserQuizRepository extends JpaRepository<UserQuiz, Integer> {
    //UserQuiz findByUserAndQuizId(User user, Integer quizId);
    List<UserQuiz> findAllByfkUser(User user);
    UserQuiz findByfkUserAndQuizId(User user, Integer quizId);
    Integer countByQuizIdAndLikeIsTrue(Integer quizId);
}
