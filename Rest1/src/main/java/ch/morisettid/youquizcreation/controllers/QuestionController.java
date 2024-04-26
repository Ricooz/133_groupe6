package ch.morisettid.youquizcreation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.morisettid.youquizcreation.dto.QuestionDTO;
import ch.morisettid.youquizcreation.exceptions.IdNotFoundException;
import ch.morisettid.youquizcreation.exceptions.UnauthorizedUserException;
import ch.morisettid.youquizcreation.services.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<QuestionDTO>> getAll() {
        return new ResponseEntity<>(questionService.findAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer pkQuestion) {
        QuestionDTO questionDTO = questionService.findQuestion(pkQuestion);
        if (questionDTO != null) {
            return new ResponseEntity<>(questionDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Quiz non trouvé. Id du quiz fournie invalide.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(@RequestParam String nom, @RequestParam Integer pkQuiz, @RequestParam String username) {
        try {
            return new ResponseEntity<>(questionService.addQuestion(nom, pkQuiz, username), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestParam Integer pkQuestion, @RequestParam String nom, @RequestParam String username) {
        try {
            return new ResponseEntity<>(questionService.updateQuestion(pkQuestion, nom, username), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam Integer pkQuestion, @RequestParam String username) {
        try {
            questionService.deleteQuestion(pkQuestion, username);
            return new ResponseEntity<>("Question supprimée avec succès.", HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}