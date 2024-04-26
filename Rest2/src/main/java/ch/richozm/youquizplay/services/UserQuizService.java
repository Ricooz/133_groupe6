package ch.richozm.youquizplay.services;

import java.util.List;

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
    public Boolean contabilisePoints(Integer userId, Integer quizId, Integer points) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }

        UserQuiz userQuiz = userQuizRepository.findByfkUserAndQuizId(user, quizId);
        if (userQuiz == null) {
            userQuiz = new UserQuiz();
            userQuiz.setUser(user);
            userQuiz.setQuiz(quizId);
            userQuiz.setLike(false);
            userQuiz.setPoints(points);
        }
        userQuiz.setPoints(points);
        userQuizRepository.save(userQuiz);

        return true;
    }

    @Transactional
    public Integer getPoints(Integer userId, Integer quizId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return -1;
        }

        // Récupérer tous les UserQuiz pour l'utilisateur donné
        UserQuiz userQuiz = userQuizRepository.findByfkUserAndQuizId(user, quizId);

        // Retourner les points
        return userQuiz.getPoints();
    }

    @Transactional
    public Integer getAllPoints(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return -1;
        }

        // Récupérer tous les UserQuiz pour l'utilisateur donné
        List<UserQuiz> userQuizzes = userQuizRepository.findAllByfkUser(user);

        // Si l'utilisateur n'a pas de UserQuiz, retourner 0
        if (userQuizzes.isEmpty()) {
            return 0;
        }

        // Calculer la somme des points pour tous les UserQuiz de l'utilisateur
        int totalPoints = userQuizzes.stream()
            .mapToInt(UserQuiz::getPoints)
            .sum();

        // Retourner le total des points
        return totalPoints;
    }
    

    @Transactional
    public Integer getNbrLikes(Integer quizId) {
        Integer nbrLikes = userQuizRepository.countByQuizIdAndLikeIsTrue(quizId);
        return nbrLikes;
    }
}
