package ch.morisettid.youquizcreation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.morisettid.youquizcreation.dto.QuizDTO;
import ch.morisettid.youquizcreation.model.Quiz;
import ch.morisettid.youquizcreation.services.QuizService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam Integer pkQuiz) {
        Quiz quiz = quizService.findQuiz(pkQuiz);
        if (quiz != null) {
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("PK Quiz invalide", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<QuizDTO>> getAll() {
        return new ResponseEntity<>(quizService.findAllQuizzes(), HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> create(@RequestParam String username) {
        return ResponseEntity.ok("Logout réussi");
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update() {
        return ResponseEntity.ok("Logout réussi");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("Logout réussi");
    }
}