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

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer pkQuiz) {
        QuizDTO quizDTO = quizService.findQuiz(pkQuiz);
        if (quizDTO != null) {
            return new ResponseEntity<>(quizDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("PK quiz invalide", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<QuizDTO> add(@RequestParam String nom, @RequestParam String description) {
        return new ResponseEntity<>(quizService.addQuiz(nom, description), HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestParam Integer pkQuiz,  String nom, @RequestParam String description) {
        QuizDTO quizDTO = quizService.updateQuiz(pkQuiz, nom, description);
        if (quizDTO != null) {
            return new ResponseEntity<>(quizDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("PK quiz invalide", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam Integer pkQuiz) {
        if (quizService.deleteQuiz(pkQuiz)) {
            return new ResponseEntity<>("Quiz supprimé avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("PK quiz invalide", HttpStatus.NOT_FOUND);
        }
    }
}