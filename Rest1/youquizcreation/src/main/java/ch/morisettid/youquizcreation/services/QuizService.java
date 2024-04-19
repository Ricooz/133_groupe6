package ch.morisettid.youquizcreation.services;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.morisettid.youquizcreation.model.Quiz;
import ch.morisettid.youquizcreation.repositories.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz findQuiz(Integer pkQuiz) {
        return quizRepository.findById(pkQuiz).orElse(null);
    }

    public Iterable<Quiz> findAllQuizzes() {
        Iterable<Quiz> quizzes = quizRepository.findAll();
        return 
    }

    @Transactional
    public Boolean addNewSkieur(String name, Integer position, Integer paysId) {
        return true;
    }
}