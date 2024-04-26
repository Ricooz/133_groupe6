package ch.richozm.youquizplay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.richozm.youquizplay.model.User;
import ch.richozm.youquizplay.model.UserQuiz;
import ch.richozm.youquizplay.repositories.UserQuizRepository;
import ch.richozm.youquizplay.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserQuizService {
    private final UserQuizRepository userQuizRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserQuizService(UserQuizRepository userQuizRepository, UserRepository userRepository) {
        this.userQuizRepository = userQuizRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String likeQuiz(Integer userId, Integer quizId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "pk invalide !";
        }
        UserQuiz userQuiz = userQuizRepository.findByfkUserAndQuizId(user, quizId);
        if (userQuiz != null) {
            userQuiz.setLike(!userQuiz.getLike());
        } else {
            userQuiz = new UserQuiz();
            userQuiz.setUser(user);
            userQuiz.setQuiz(quizId);
            userQuiz.setLike(true);
        }
        userQuizRepository.save(userQuiz);
        return "Like sauvegardé !";
    }

    @Transactional
    public String contabilisePoints(Integer userId, Integer quizId, Integer points) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "pk invalide !";
        }
        UserQuiz userQuiz = userQuizRepository.findByfkUserAndQuizId(user, quizId);
        if (userQuiz != null) {
            userQuiz.setPoints(points);
        }
        return "Points ajoutés avec succès";
    }
}
