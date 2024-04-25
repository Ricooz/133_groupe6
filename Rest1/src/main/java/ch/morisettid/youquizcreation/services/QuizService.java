package ch.morisettid.youquizcreation.services;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.morisettid.youquizcreation.dto.QuizDTO;
import ch.morisettid.youquizcreation.exceptions.IdNotFoundException;
import ch.morisettid.youquizcreation.exceptions.UnauthorizedUserException;
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

    public Iterable<QuizDTO> findUserQuizzes(Integer pkUser) {
        Iterable<Quiz> quizzes = quizRepository.findAll();
        List<QuizDTO> quizDTOs = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            quizDTOs.add(quiz.toDTO());
        }

        return quizDTOs;
    }

    @Transactional
    public QuizDTO addQuiz(String nom, String description, String username) {
        Quiz quiz = new Quiz();
        quiz.setNom(nom);
        quiz.setDescription(description);
        quiz.setUsername(username);
        quizRepository.save(quiz);
        return quiz.toDTO();
    }

    @Transactional
    public QuizDTO updateQuiz(Integer pkQuiz, String nom, String description, String username) throws IdNotFoundException, UnauthorizedUserException {
        Quiz quiz = quizRepository.findById(pkQuiz).orElse(null);
        QuizDTO quizDTO = null;

        if (quiz != null) { // Vérifie si la pk est valide
            if (quiz.getUsername().equals(username)) { // Change seulement si l'utilisateur le détient
                quiz.setNom(nom);
                quiz.setDescription(description);
                quizRepository.save(quiz);
                quizDTO = quiz.toDTO();
            } else {
                throw new UnauthorizedUserException("Tentative non autorisée de mise à jour du quiz. Le quiz n'appartient pas au nom d'utilisateur fourni.");
            }
        } else {
            throw new IdNotFoundException("Quiz non trouvé. Id du quiz fournie invalide.");
        }

        return quizDTO;
    }

    @Transactional
    public void deleteQuiz(Integer pkQuiz, String username) throws UnauthorizedUserException, IdNotFoundException {
        Quiz quiz = quizRepository.findById(pkQuiz).orElse(null);

        if (quiz != null) { // Vérifie si la pk est valide
            if (quiz.getUsername().equals(username)) { // Change seulement si l'utilisateur le détient
                quizRepository.deleteById(pkQuiz);
            } else {
                throw new UnauthorizedUserException("Tentative non autorisée de suppresion du quiz. Le quiz n'appartient pas au nom d'utilisateur fourni.");
            }
        } else {
            throw new IdNotFoundException("Quiz non trouvé. Id du quiz fournie invalide.");
        }
    }
}