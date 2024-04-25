package ch.morisettid.youquizcreation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.morisettid.youquizcreation.dto.QuestionDTO;
import ch.morisettid.youquizcreation.exceptions.IdNotFoundException;
import ch.morisettid.youquizcreation.exceptions.UnauthorizedUserException;
import ch.morisettid.youquizcreation.model.Question;
import ch.morisettid.youquizcreation.model.Quiz;
import ch.morisettid.youquizcreation.repositories.QuestionRepository;
import ch.morisettid.youquizcreation.repositories.QuizRepository;
import jakarta.transaction.Transactional;

@Service
public class QuestionService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public QuestionDTO findQuestion(Integer pkQuestion) {
        Question question = questionRepository.findById(pkQuestion).orElse(null);
        QuestionDTO questionDTO = null;
        if (question != null) {
            questionDTO = question.toDTO();
        }

        return questionDTO;
    }

    public Iterable<QuestionDTO> findAllQuestions() {
        Iterable<Question> questions = questionRepository.findAll();
        List<QuestionDTO> questionsDTOs = new ArrayList<>();

        for (Question question : questions) {
            questionsDTOs.add(question.toDTO());
        }

        return questionsDTOs;
    }

    @Transactional
    public QuestionDTO addQuestion(String nom, Integer pkQuiz, String username) throws IdNotFoundException, UnauthorizedUserException {
        QuestionDTO questionDTO = null;
        Quiz quiz = quizRepository.findById(pkQuiz).orElse(null);

        if (quiz != null) { // Vérifie si la pk est valide
            if (quiz.getUsername().equals(username)) { // Change seulement si l'utilisateur le détient
                Question question = new Question();
                question.setNom(nom);
                question.setQuiz(quiz);
                questionRepository.save(question);
                questionDTO = question.toDTO();
            } else {
                throw new UnauthorizedUserException("Tentative non autorisée de création de la question. Le quiz n'appartient pas au nom d'utilisateur fourni.");
            }
        } else {
            throw new IdNotFoundException("Question non trouvés. Id de la question fournie invalide.");
        }

        return questionDTO;
    }

    @Transactional
    public QuestionDTO updateQuestion(Integer pkQuestion, String nom, String username) throws UnauthorizedUserException, IdNotFoundException {
        Question question = questionRepository.findById(pkQuestion).orElse(null);
        QuestionDTO questionDTO = null;

        if (question != null) { // Vérifie si la pk est valide
            if (question.getQuiz().getUsername().equals(username)) { // Change seulement si l'utilisateur le détient
                question.setNom(nom);
                questionRepository.save(question);
                questionDTO = question.toDTO();
            } else {
                throw new UnauthorizedUserException("Tentative non autorisée de mise à jour de la question. La question n'appartient pas au nom d'utilisateur fourni.");
            }
        } else {
            throw new IdNotFoundException("Question non trouvée. Id de la question fournie invalide.");
        }

        return questionDTO;
    }

    @Transactional
    public void deleteQuestion(Integer pkQuestion, String username) throws UnauthorizedUserException, IdNotFoundException {
        Question question = questionRepository.findById(pkQuestion).orElse(null);

        if (question != null) { // Vérifie si la pk est valide
            if (question.getQuiz().getUsername().equals(username)) { // Change seulement si l'utilisateur le détient
                questionRepository.deleteById(pkQuestion);
            } else {
                throw new UnauthorizedUserException("Tentative non autorisée de suppresion de la question. La question n'appartient pas au nom d'utilisateur fourni.");
            }
        } else {
            throw new IdNotFoundException("Question non trouvée. Id de la question fournie invalide.");
        }
    }
}
