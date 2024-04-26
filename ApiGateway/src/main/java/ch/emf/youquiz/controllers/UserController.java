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
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return ResponseEntity.ok(Collections.singletonMap("username", username));
        } else {
            return ResponseEntity.ok("Aucun utilisateur n'est connecté.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        ResponseEntity<Boolean> response = restTemplate.getForEntity(baseURLRest2 + "/user/login", Boolean.class, params);

        // Vérifie si la requête est réussie
        if (response.getBody()) {
            session.setAttribute("user", username);
            return ResponseEntity.ok(Collections.singletonMap("username", username));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Nom d'utilisateur ou mot passe invalide."));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // Vérifie si l'utilisateur est connecté
        if (session.getAttribute("username") != null) {
            session.invalidate();
            return ResponseEntity.ok("Déconnexion réussie.");
        } else {
            return ResponseEntity.ok("Aucun utilisateur n'est connecté");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(HttpSession session, @RequestParam String username, @RequestParam String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        ResponseEntity<Boolean> response = restTemplate.getForEntity(baseURLRest2 + "/user/add", Boolean.class, params);
        
        // Vérifie si la requête est réussie
        if (response.getBody()) {
            session.setAttribute("user", username);
            return ResponseEntity.ok(Collections.singletonMap("username", username));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("error", "Nom d'utilisateur déja utilisé."));
        }
    }
}
