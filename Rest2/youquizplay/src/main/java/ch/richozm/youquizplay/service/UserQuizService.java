package ch.richozm.youquizplay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.richozm.youquizplay.model.User;
import ch.richozm.youquizplay.model.UserQuiz;
import ch.richozm.youquizplay.repository.UserQuizRepository;
import ch.richozm.youquizplay.repository.UserRepository;
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
    public void likeQuiz(Integer userId, Integer quizId) {
        //recheche user selon son id
        User user = userRepository.findBypkUser(userId);
        UserQuiz userQuiz = userQuizRepository.findByfkUserAndQuizId(user, quizId);

        if (userQuiz != null) {
            userQuiz = new UserQuiz();
            userQuiz.setLike(!userQuiz.getLike());
            userQuiz.setUser(user);
            userQuiz.setQuiz(quizId);
            userQuizRepository.save(userQuiz);
        }
    }

    @Transactional
    public String contabilisePoints(Integer userId, Integer quizId, Integer points) {
        // Rechercher l'utilisateur par son identifiant unique
        User user = userRepository.findBypkUser(userId);
        UserQuiz userQuiz = userQuizRepository.findByfkUserAndQuizId(user, quizId);
        if (userQuiz != null) {
            userQuiz.setPoints(points);
        }
        return "Points ajoutés avec succès";
    }
}
