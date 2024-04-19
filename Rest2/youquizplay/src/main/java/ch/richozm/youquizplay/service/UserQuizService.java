package ch.richozm.youquizplay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.richozm.youquizplay.repository.UserQuizRepository;
import jakarta.transaction.Transactional;

@Service
public class UserQuizService {
    private final UserQuizRepository userQuizRepository;

    @Autowired
    public UserQuizService(UserQuizRepository userQuizRepository) {
        this.userQuizRepository = userQuizRepository;
    }

    @Transactional
    public void likeClick(String quizTitle) {

    }

    @Transactional
    public String validateQuiz(String username, String password) {
        return "";
    }
    
}
