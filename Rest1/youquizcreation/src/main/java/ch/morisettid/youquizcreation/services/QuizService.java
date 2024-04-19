package ch.morisettid.youquizcreation.services;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.morisettid.youquizcreation.dto.QuestionDTO;
import ch.morisettid.youquizcreation.dto.QuizDTO;
import ch.morisettid.youquizcreation.dto.ReponseDTO;
import ch.morisettid.youquizcreation.model.Question;
import ch.morisettid.youquizcreation.model.Quiz;
import ch.morisettid.youquizcreation.model.Reponse;
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

    public Iterable<QuizDTO> findAllQuizzes() {
        Iterable<Quiz> quizzes = quizRepository.findAll();
        List<QuizDTO> quizDTOs = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            List<QuestionDTO> questionDTOs = new ArrayList<>();
            for (Question question : quiz.getQuestions()) {
                List<ReponseDTO> reponseDTOs = new ArrayList<>();
                for (Reponse reponse : question.getReponses()) {
                    reponseDTOs.add(new ReponseDTO(reponse.getPkReponse(), reponse.getNom(),  reponse.isCorrect()));
                }
                questionDTOs.add(new QuestionDTO(question.getPkQuestion(), question.getNom(), reponseDTOs));
            }
            quizDTOs.add(new QuizDTO(quiz.getPkQuiz(), quiz.getNom(), quiz.getDescription(), questionDTOs));
        }

        return quizDTOs;
    }

    @Transactional
    public Boolean addNewSkieur(String name, Integer position, Integer paysId) {
        return true;
    }
}