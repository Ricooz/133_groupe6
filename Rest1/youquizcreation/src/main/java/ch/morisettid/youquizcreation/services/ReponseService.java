package ch.morisettid.youquizcreation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.morisettid.youquizcreation.dto.QuestionDTO;
import ch.morisettid.youquizcreation.dto.ReponseDTO;
import ch.morisettid.youquizcreation.model.Question;
import ch.morisettid.youquizcreation.model.Quiz;
import ch.morisettid.youquizcreation.model.Reponse;
import ch.morisettid.youquizcreation.repositories.QuestionRepository;
import ch.morisettid.youquizcreation.repositories.QuizRepository;
import ch.morisettid.youquizcreation.repositories.ReponseRepository;
import jakarta.transaction.Transactional;

@Service
public class ReponseService {

    private final QuestionRepository questionRepository;
    private final ReponseRepository reponseRepository;

    @Autowired
    public ReponseService(QuestionRepository questionRepository, ReponseRepository reponseRepository) {
        this.questionRepository = questionRepository;
        this.reponseRepository = reponseRepository;
    }

    public ReponseDTO findReponse(Integer pkReponse) {
        Reponse reponse = reponseRepository.findById(pkReponse).orElse(null);
        ReponseDTO reponseDTO = null;
        if (reponse != null) {
            reponseDTO = reponse.toDTO();
        }

        return reponseDTO;
    }

    public Iterable<ReponseDTO> findAllReponses() {
        Iterable<Reponse> reponses = reponseRepository.findAll();
        List<ReponseDTO> reponsesDTOs = new ArrayList<>();

        for (Reponse reponse : reponses) {
            reponsesDTOs.add(reponse.toDTO());
        }

        return reponsesDTOs;
    }

    @Transactional
    public ReponseDTO addReponse(String nom, Boolean correct, Integer pkQuestion) {
        ReponseDTO reponseDTO = null;
        Question question = questionRepository.findById(pkQuestion).orElse(null);
        if (question != null) {
            Reponse reponse = new Reponse();
            reponse.setNom(nom);
            reponse.setCorrect(correct);
            reponseRepository.save(reponse);
            reponseDTO = reponse.toDTO();
        }
        
        return reponseDTO;
    }

    @Transactional
    public ReponseDTO updateReponse(Integer pkReponse, String nom, Boolean correct) {
        Reponse reponse = reponseRepository.findById(pkReponse).orElse(null);
        ReponseDTO reponseDTO = null;
        if (reponse != null) {
            reponse.setNom(nom);
            reponse.setCorrect(correct);
            reponseRepository.save(reponse);
            reponseDTO = reponse.toDTO();
        }

        return reponseDTO;
    }

    @Transactional
    public Boolean deleteReponse(Integer pkReponse) {
        Boolean exist = reponseRepository.existsById(pkReponse);
        if (exist) {
            reponseRepository.deleteById(pkReponse);
        }

        return exist;
    }
}
