package ch.emf.youquiz.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        ResponseEntity<User> response = restTemplate.getForEntity(baseURLRest2 + "/user/login", User.class, params);

        // Vérifie si la requête est réussie
        User user = response.getBody();
        if (response.getStatusCode().is2xxSuccessful()) {
            session.setAttribute("user", user);
            return ResponseEntity.ok(Collections.singletonMap("username", user.getUsername()));
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        ResponseEntity<User> response = restTemplate.getForEntity(baseURLRest2 + "/user/add", User.class, params);
        
        // Vérifie si la requête est réussie
        User user = response.getBody();
        if (response.getStatusCode().is2xxSuccessful()) {
            session.setAttribute("user", user);
            return ResponseEntity.ok(Collections.singletonMap("username", user.getUsername()));
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    @GetMapping("/points")
    public ResponseEntity<String> getPoints(HttpSession session) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        if (username != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("userId", String.valueOf(user.getPKUser()));

            ResponseEntity<String> response = restTemplate.getForEntity(baseURLRest2 + "/userquiz/points/getAll", String.class, params);
            return ResponseEntity.ok(response.getBody());
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour avoir les points d'un utilisateur.", HttpStatus.FORBIDDEN);
        }
    }
}
