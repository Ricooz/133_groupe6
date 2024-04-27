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

import ch.morisettid.youquizcreation.dto.QuizDTO;
import ch.morisettid.youquizcreation.exceptions.IdNotFoundException;
import ch.morisettid.youquizcreation.exceptions.UnauthorizedUserException;
import ch.morisettid.youquizcreation.services.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<QuizDTO>> getAll() {
        return new ResponseEntity<>(quizService.findAllQuizzes(), HttpStatus.OK);
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<?> get(@PathVariable("quizId") Integer pkQuiz) {
        QuizDTO quizDTO = quizService.findQuiz(pkQuiz);
        if (quizDTO != null) {
            return new ResponseEntity<>(quizDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Quiz non trouvé. Id du quiz fournie invalide.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Iterable<QuizDTO>> getUserQuizzes(@PathVariable("username") String username) {
        return new ResponseEntity<>(quizService.findUserQuizzes(username), HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<QuizDTO> add(@RequestParam String nom, @RequestParam String description, @RequestParam String username) {
        return new ResponseEntity<>(quizService.addQuiz(nom, description, username), HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestParam Integer pkQuiz, @RequestParam String nom, @RequestParam String description, @RequestParam String username) {
        try {
            return new ResponseEntity<>(quizService.updateQuiz(pkQuiz, nom, description, username), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam Integer pkQuiz, @RequestParam String username) {
        try {
            quizService.deleteQuiz(pkQuiz, username);
            return new ResponseEntity<>("Quiz supprimé avec succès.", HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}