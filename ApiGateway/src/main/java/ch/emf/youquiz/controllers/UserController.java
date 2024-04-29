package ch.emf.youquiz.controllers;

import java.util.Collections;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ch.emf.youquiz.beans.Quiz;
import ch.emf.youquiz.beans.User;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Environment env;
    private final RestTemplate restTemplate;
    private final String baseURLRest2;

    @Autowired
    public UserController(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
        this.baseURLRest2 = env.getProperty("rest2.url");
    }

    @GetMapping("")
    public ResponseEntity<?> isConnected(HttpSession session) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(Collections.singletonMap("username", user.getUsername()));
        } else {
            return ResponseEntity.ok("Aucun utilisateur n'est connecté.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        
        try {
            ResponseEntity<User> response = restTemplate.exchange(baseURLRest2 + "/user/login", HttpMethod.POST, requestEntity, User.class);
            User user = response.getBody();

            session.setAttribute("user", user);
            return ResponseEntity.ok(Collections.singletonMap("username", user.getUsername()));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(e.getStatusCode()).body("Nom d'utilisateur ou mot passe invalide.");
            } else {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // Vérifie si l'utilisateur est connecté
        if (session.getAttribute("user") != null) {
            session.invalidate();
            return ResponseEntity.ok("Déconnexion réussie.");
        } else {
            return ResponseEntity.ok("Aucun utilisateur n'est connecté.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(HttpSession session, @RequestParam String username, @RequestParam String password) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<User> response = restTemplate.postForEntity(baseURLRest2 + "/user/add", requestEntity, User.class);
            User user = response.getBody();

            session.setAttribute("user", user);
            return ResponseEntity.ok(Collections.singletonMap("username", user.getUsername()));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    @GetMapping("/points")
    public ResponseEntity<String> getPoints(HttpSession session) {
       // Vérifie si l'utilisateur est connecté
       User user = (User) session.getAttribute("user");
       if (user != null) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURLRest2 + "/userquiz/points/getAll")
                .queryParam("userId", user.getPKUser());

            try {
                ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);
                return ResponseEntity.ok(response.getBody());
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour avoir les points d'un utilisateur.", HttpStatus.FORBIDDEN);
        }
    }
}
