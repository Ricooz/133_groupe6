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
    public void likeQuiz(String username, String quizTitle, Boolean liked) {
        UserQuiz userQuiz = userQuizRepository.findByQuizTitle(quizTitle);
        User user = userRepository.findByUsername(username);
        userQuiz.setUser(user.getPKUser());
        userQuiz.setQuiz(userQuiz.getQuiz());
        userQuiz.setLike(liked);
        userQuizRepository.save(userQuiz);
    }

    @Transactional
    public void unLikeQuiz(Integer userId, Integer quizId, Boolean liked) {
        UserQuiz userQuiz = userQuizRepository.findByUserIdAndQuizId(userId, quizId);
        if (userQuiz != null) {
            userQuizRepository.delete(userQuiz);
        }
    }

    @Transactional
    public String validateQuiz(Integer quizId) {
        return "";
    }
    
}
