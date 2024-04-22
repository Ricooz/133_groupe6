package ch.morisettid.youquizcreation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.morisettid.youquizcreation.dto.QuestionDTO;
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
    public QuestionDTO addQuestion(String nom, Integer pkQuiz) {
        QuestionDTO questionDTO = null;
        Quiz quiz = quizRepository.findById(pkQuiz).orElse(null);
        if (quiz != null) {
            Question question = new Question();
            question.setNom(nom);
            questionRepository.save(question);
            questionDTO = question.toDTO();
        }
        
        return questionDTO;
    }

    @Transactional
    public QuestionDTO updateQuestion(Integer pkQuestion, String nom) {
        Question question = questionRepository.findById(pkQuestion).orElse(null);
        QuestionDTO questionDTO = null;
        if (question != null) {
            question.setNom(nom);
            questionRepository.save(question);
            questionDTO = question.toDTO();
        }

        return questionDTO;
    }

    @Transactional
    public Boolean deleteQuestion(Integer pkQuestion) {
        Boolean exist = questionRepository.existsById(pkQuestion);
        if (exist) {
            quizRepository.deleteById(pkQuestion);
        }

        return exist;
    }
}
