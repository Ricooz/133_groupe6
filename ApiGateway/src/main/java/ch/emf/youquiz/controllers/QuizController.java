package ch.emf.youquiz.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.emf.youquiz.beans.Quiz;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final RestTemplate restTemplate;

    @Autowired
    public QuizController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Quiz>> getAll() {
        ResponseEntity<Quiz[]> response = restTemplate.getForEntity("http://youquizrest1:8080/quiz", Quiz[].class);
        List<Quiz> quizList = new ArrayList<>(Arrays.asList(response.getBody()));

        // For each quiz, get the number of likes from rest 2
        for (Quiz quiz : quizList) {
            String likesResourceUrl = "http://youquizrest2:8080/likes/" + quiz.getPkQuiz();
            ResponseEntity<Integer> likesResponse = restTemplate.getForEntity(likesResourceUrl, Integer.class);
            
            quiz.setLikes(likesResponse.getBody());
        }

        return ResponseEntity.ok(quizList);
    }
}