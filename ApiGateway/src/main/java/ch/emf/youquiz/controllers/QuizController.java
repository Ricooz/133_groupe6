package ch.emf.youquiz.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.emf.youquiz.beans.Question;
import ch.emf.youquiz.beans.Quiz;
import ch.emf.youquiz.beans.QuizSubmission;
import ch.emf.youquiz.beans.Reponse;
import ch.emf.youquiz.beans.User;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final Environment env;
    private final RestTemplate restTemplate;
    private final String baseURLRest1;
    private final String baseURLRest2;

    @Autowired
    public QuizController(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
        this.baseURLRest1 = env.getProperty("rest1.url") + "/quiz";
        this.baseURLRest2 = env.getProperty("rest2.url");
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Quiz>> getAll() {
        ResponseEntity<Quiz[]> response = restTemplate.getForEntity(baseURLRest1, Quiz[].class);
        List<Quiz> quizList = new ArrayList<>(Arrays.asList(response.getBody()));

        for (Quiz quiz : quizList) {
            refreshLike(quiz);
        }

        return ResponseEntity.ok(quizList);
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<Quiz> get(@PathVariable("quizId") Integer pkQuiz) {
        ResponseEntity<Quiz> response = restTemplate.getForEntity(baseURLRest1 + "/get/" + pkQuiz, Quiz.class);

        // Vérifie si la requête est réussie
        if (response.getStatusCode().is2xxSuccessful()) {
            Quiz quiz = response.getBody();
            refreshLike(quiz);
            return ResponseEntity.ok(quiz);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<Quiz>> getUserQuizzes(@PathVariable("userId") Integer pkUser) {
        ResponseEntity<Quiz[]> response = restTemplate.getForEntity(baseURLRest1 + "/user/" + pkUser, Quiz[].class);
        List<Quiz> quizList = new ArrayList<>(Arrays.asList(response.getBody()));

        for (Quiz quiz : quizList) {
            refreshLike(quiz);
        }

        return ResponseEntity.ok(quizList);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(HttpSession session, @RequestParam String nom, @RequestParam String description) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("username");
        String username = user.getUsername();
        if (username != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("nom", nom);
            params.put("description", description);
            params.put("username", username);

            ResponseEntity<Quiz> response = restTemplate.getForEntity(baseURLRest1 + "/add", Quiz.class);
            
            return ResponseEntity.ok(response.getBody());
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour l'ajout d'un quiz.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(HttpSession session, @RequestParam Integer pkQuiz, @RequestParam String nom, @RequestParam String description) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("username");
        String username = user.getUsername();
        if (username != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("pkQuiz", String.valueOf(pkQuiz));
            params.put("nom", nom);
            params.put("description", description);
            params.put("username", username);

            ResponseEntity<Quiz> response = restTemplate.getForEntity(baseURLRest1 + "/update", Quiz.class);

            // Vérifie si la requête est réussie
            if (response.getStatusCode().is2xxSuccessful()) {
                Quiz quiz = response.getBody();
                refreshLike(quiz);
                return ResponseEntity.ok(quiz);
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la modifcation d'un quiz.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(HttpSession session, @RequestParam Integer pkQuiz) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("username");
        String username = user.getUsername();
        if (username != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("pkQuiz", String.valueOf(pkQuiz));
            params.put("username", username);

            ResponseEntity<String> response = restTemplate.getForEntity(baseURLRest1 + "/delete", String.class);

            // Vérifie si la requête est réussie
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la suppression d'un quiz.", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path = "/like")
    public ResponseEntity<String> like(@RequestParam String nom, @RequestParam String description, @RequestParam String username) {
        ResponseEntity<String> response = restTemplate.getForEntity(baseURLRest2 + "/userquiz/like", String.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping(path = "/submit")
    public ResponseEntity<?> submit(@RequestBody QuizSubmission quizSubmission) {
        ResponseEntity<Quiz> response = restTemplate.getForEntity(baseURLRest2 + "/get/" + quizSubmission.getPkQuiz(), Quiz.class);
        Quiz quiz = response.getBody();

        int points = 0;

        // Parcourir chaque question dans quizSubmission
        for (Question submittedQuestion : quizSubmission.getQuestions()) {
            // Trouver la question correspondante dans le quiz
            Question quizQuestion = quiz.getQuestions().stream()
                .filter(q -> q.getPkQuestion().equals(submittedQuestion.getPkQuestion()))
                .findFirst()
                .orElse(null);

            // Si la question n'existe pas dans le quiz, la passer
            if (quizQuestion == null) {
                continue;
            }

            // Parcourir chaque réponse dans submittedQuestion
            for (Reponse submittedResponse : submittedQuestion.getReponses()) {
                // Trouver la réponse correspondante dans quizQuestion
                Reponse quizResponse = quizQuestion.getReponses().stream()
                    .filter(r -> r.getPkReponse().equals(submittedResponse.getPkReponse()))
                    .findFirst()
                    .orElse(null);

                // Si la réponse n'existe pas dans quizQuestion, la passer
                if (quizResponse == null) {
                    continue;
                }

                // Si la réponse soumise est correcte, incrémenter les points
                if (submittedResponse.isCorrect() && quizResponse.isCorrect()) {
                    points++;
                }
            }
        }

        // Ajoute les points
        

        // Retourner le total des points
        return ResponseEntity.ok(points);
    }

    @GetMapping(path = "/points")
    public ResponseEntity<?> points(@RequestParam String username) {
        
    }

    public void refreshLike(Quiz quiz) {
        String likesResourceUrl = baseURLRest2 + "/userquiz/likes/" + quiz.getPkQuiz();
        ResponseEntity<Integer> likesResponse = restTemplate.getForEntity(likesResourceUrl, Integer.class);

        quiz.setLikes(likesResponse.getBody());
    }
}