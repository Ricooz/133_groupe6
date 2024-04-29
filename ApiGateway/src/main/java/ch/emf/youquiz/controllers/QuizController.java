package ch.emf.youquiz.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<?> get(@PathVariable("quizId") Integer pkQuiz) {
        try {
            ResponseEntity<Quiz> response = restTemplate.getForEntity(baseURLRest1 + "/get/" + pkQuiz, Quiz.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Iterable<Quiz>> getUserQuizzes(@PathVariable("username") String username) {
        ResponseEntity<Quiz[]> response = restTemplate.getForEntity(baseURLRest1 + "/user/" + username, Quiz[].class);
        List<Quiz> quizList = new ArrayList<>(Arrays.asList(response.getBody()));

        for (Quiz quiz : quizList) {
            refreshLike(quiz);
        }

        return ResponseEntity.ok(quizList);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(HttpSession session, @RequestParam String nom, @RequestParam String description) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();

            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("nom", nom);
            params.add("description", description);
            params.add("username", username);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            ResponseEntity<Quiz> response = restTemplate.postForEntity(baseURLRest1 + "/add", requestEntity, Quiz.class);
            
            return ResponseEntity.ok(response.getBody());
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour l'ajout d'un quiz.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(HttpSession session, @RequestParam Integer pkQuiz, @RequestParam String nom, @RequestParam String description) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();

            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("pkQuiz", String.valueOf(pkQuiz));
            params.add("nom", nom);
            params.add("description", description);
            params.add("username", username);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            try {
                ResponseEntity<Quiz> response = restTemplate.exchange(baseURLRest1 + "/update", HttpMethod.PUT, requestEntity, Quiz.class);
                Quiz quiz = response.getBody();
                refreshLike(quiz);

                return ResponseEntity.ok(quiz);
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la modifcation d'un quiz.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(HttpSession session, @RequestParam Integer pkQuiz) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURLRest1 + "/delete")
                .queryParam("pkQuiz", pkQuiz)
                .queryParam("username", username);

            try {
                ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, null, String.class);
                
                return ResponseEntity.ok(response.getBody());
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la suppression d'un quiz.", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path = "/like/{id}")
    public ResponseEntity<String> like(HttpSession session, @PathVariable("id") Integer pkQuiz) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            try {
                restTemplate.getForEntity(baseURLRest1 + "/get/" + pkQuiz, Quiz.class);
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }

            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("userId", String.valueOf(user.getPKUser()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(baseURLRest2 + "/userquiz/like/" + String.valueOf(pkQuiz), requestEntity, String.class);

            return ResponseEntity.ok(response.getBody());
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour le like d'un quiz.", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path = "/submit")
    public ResponseEntity<?> submit(HttpSession session, @RequestBody QuizSubmission quizSubmission) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Quiz quiz;
            try {
                ResponseEntity<Quiz> response = restTemplate.getForEntity(baseURLRest1 + "/get/" + quizSubmission.getPkQuiz(), Quiz.class);
                quiz = response.getBody();
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }

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
            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("userId", String.valueOf(user.getPKUser()));
            params.add("quizId", String.valueOf(quiz.getPkQuiz()));
            params.add("points", String.valueOf(points));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            try {
                restTemplate.postForEntity(baseURLRest1 + "/userquiz/points/set", requestEntity, Quiz.class);
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }

            // Retourner le total des points
            return ResponseEntity.ok(points);
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour soumettre un quiz.", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/points")
    public ResponseEntity<?> points(HttpSession session, @RequestParam Integer pkQuiz) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            try {
                restTemplate.getForEntity(baseURLRest1 + "/get/" + pkQuiz, Quiz.class);
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURLRest2 + "/userquiz/points/get")
                .queryParam("userId", user.getPKUser())
                .queryParam("quizId", pkQuiz);

            try {
                ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);
                return ResponseEntity.ok(response.getBody());
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour avoir les points d'un quiz.", HttpStatus.FORBIDDEN);
        }
    }

    public void refreshLike(Quiz quiz) {
        String likesResourceUrl = baseURLRest2 + "/userquiz/likes/" + quiz.getPkQuiz();
        ResponseEntity<Integer> likesResponse = restTemplate.getForEntity(likesResourceUrl, Integer.class);

        quiz.setLikes(likesResponse.getBody());
    }
}