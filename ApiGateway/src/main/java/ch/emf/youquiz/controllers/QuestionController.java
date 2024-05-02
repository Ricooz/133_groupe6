package ch.emf.youquiz.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ch.emf.youquiz.beans.Question;
import ch.emf.youquiz.beans.User;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins =  {"https://morisettid.emf-informatique.ch", "https://richozm.emf-informatique.ch"}, allowCredentials = "true")
@RestController
@RequestMapping("/question")
public class QuestionController {

    private final Environment env;
    private final RestTemplate restTemplate;
    private final String baseURLRest1;

    @Autowired
    public QuestionController(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
        this.baseURLRest1 = env.getProperty("rest1.url") + "/question";
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Question>> getAll() {
        ResponseEntity<Question[]> response = restTemplate.getForEntity(baseURLRest1, Question[].class);
        List<Question> questionList = new ArrayList<>(Arrays.asList(response.getBody()));

        return ResponseEntity.ok(questionList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer pkQuestion) {
        try {
            ResponseEntity<Question> response = restTemplate.getForEntity(baseURLRest1 + "/get/" + pkQuestion, Question.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(HttpSession session, @RequestParam String nom, @RequestParam Integer pkQuiz) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();

            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("nom", nom);
            params.add("pkQuiz", String.valueOf(pkQuiz));
            params.add("username", username);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            try {
                ResponseEntity<Question> response = restTemplate.postForEntity(baseURLRest1 + "/add", requestEntity, Question.class);
                return ResponseEntity.ok(response.getBody());
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour l'ajout d'une question.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(HttpSession session, @RequestParam Integer pkQuestion,  String nom) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();

            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("pkQuestion", String.valueOf(pkQuestion));
            params.add("nom", nom);
            params.add("username", username);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            try {
                ResponseEntity<Question> response = restTemplate.exchange(baseURLRest1 + "/update", HttpMethod.PUT, requestEntity, Question.class);
                return ResponseEntity.ok(response.getBody());
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la modifcation d'une question.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(HttpSession session, @RequestParam Integer pkQuestion) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURLRest1 + "/delete")
                .queryParam("pkQuestion", pkQuestion)
                .queryParam("username", username);

            try {
                ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, null, String.class);
                
                return ResponseEntity.ok(response.getBody());
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la suppression d'une question.", HttpStatus.FORBIDDEN);
        }
    }
}