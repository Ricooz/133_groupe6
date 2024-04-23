package ch.morisettid.youquizcreation.services;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.morisettid.youquizcreation.dto.QuizDTO;
import ch.morisettid.youquizcreation.model.Quiz;
import ch.morisettid.youquizcreation.repositories.QuizRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuizDTO findQuiz(Integer pkQuiz) {
        Quiz quiz = quizRepository.findById(pkQuiz).orElse(null);
        QuizDTO quizDTO = null;
        if (quiz != null) {
            quizDTO = quiz.toDTO();
        }

        return quizDTO;
    }

    public Iterable<QuizDTO> findAllQuizzes() {
        Iterable<Quiz> quizzes = quizRepository.findAll();
        List<QuizDTO> quizDTOs = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            quizDTOs.add(quiz.toDTO());
        }

        return quizDTOs;
    }

    @Transactional
    public QuizDTO addQuiz(String nom, String description) {
        Quiz quiz = new Quiz();
        quiz.setNom(nom);
        quiz.setDescription(description);
        quizRepository.save(quiz);
        return quiz.toDTO();
    }

    @Transactional
    public QuizDTO updateQuiz(Integer pkQuiz, String nom, String description) {
        Quiz quiz = quizRepository.findById(pkQuiz).orElse(null);
        QuizDTO quizDTO = null;
        if (quiz != null) {
            quiz.setNom(nom);
            quiz.setDescription(description);
            quizRepository.save(quiz);
            quizDTO = quiz.toDTO();
        }

        return quizDTO;
    }

    @Transactional
    public Boolean deleteQuiz(Integer pkQuiz) {
        Boolean exist = quizRepository.existsById(pkQuiz);
        if (exist) {
            quizRepository.deleteById(pkQuiz);
        }

        return exist;
    }
}