package ch.morisettid.youquizcreation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.morisettid.youquizcreation.dto.ReponseDTO;
import ch.morisettid.youquizcreation.exceptions.IdNotFoundException;
import ch.morisettid.youquizcreation.exceptions.UnauthorizedUserException;
import ch.morisettid.youquizcreation.model.Question;
import ch.morisettid.youquizcreation.model.Reponse;
import ch.morisettid.youquizcreation.repositories.QuestionRepository;
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
    public ReponseDTO addReponse(String nom, Boolean correct, Integer pkQuestion, String username) throws UnauthorizedUserException, IdNotFoundException {
        ReponseDTO reponseDTO = null;
        Question question = questionRepository.findById(pkQuestion).orElse(null);

        if (question != null) { // Vérifie si la pk est valide
            if (question.getQuiz().getUsername().equals(username)) { // Change seulement si l'utilisateur le détient
                Reponse reponse = new Reponse();
                reponse.setNom(nom);
                reponse.setQuestion(question);
                reponse.setCorrect(correct);
                reponseRepository.save(reponse);
                reponseDTO = reponse.toDTO();  
            } else {
                throw new UnauthorizedUserException("Tentative non autorisée de création de la réponse. La question n'appartient pas au nom d'utilisateur fourni.");
            }
        } else {
            throw new IdNotFoundException("Réponse non trouvée. Id de la réponse fournie invalide.");
        }
        
        return reponseDTO;
    }

    @Transactional
    public ReponseDTO updateReponse(Integer pkReponse, String nom, Boolean correct, String username) throws UnauthorizedUserException, IdNotFoundException {
        Reponse reponse = reponseRepository.findById(pkReponse).orElse(null);
        ReponseDTO reponseDTO = null;

        if (reponse != null) {
            if (reponse.getQuestion().getQuiz().getUsername().equals(username)) { // Change seulement si l'utilisateur le détient
                reponse.setNom(nom);
                reponse.setCorrect(correct);
                reponseRepository.save(reponse);
                reponseDTO = reponse.toDTO();
            } else {
                throw new UnauthorizedUserException("Tentative non autorisée de mise à jour de la réponse. La réponse n'appartient pas au nom d'utilisateur fourni.");
            }
        } else {
            throw new IdNotFoundException("Réponse non trouvée. Id de la réponse fournie invalide.");
        }

        return reponseDTO;
    }

    @Transactional
    public void deleteReponse(Integer pkReponse, String username) throws UnauthorizedUserException, IdNotFoundException {
        Reponse reponse = reponseRepository.findById(pkReponse).orElse(null);

        if (reponse != null) { // Vérifie si la pk est valide
            if (reponse.getQuestion().getQuiz().getUsername().equals(username)) { // Change seulement si l'utilisateur le détient
                reponseRepository.deleteById(pkReponse);
            } else {
                throw new UnauthorizedUserException("Tentative non autorisée de suppresion de la question. La question n'appartient pas au nom d'utilisateur fourni.");
            }
        } else {
            throw new IdNotFoundException("Question non trouvée. Id de la question fournie invalide.");
        }
    }
}
