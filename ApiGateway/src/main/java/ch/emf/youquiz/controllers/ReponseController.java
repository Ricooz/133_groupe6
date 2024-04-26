package ch.emf.youquiz.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.emf.youquiz.beans.Question;
import ch.emf.youquiz.beans.Reponse;
import ch.emf.youquiz.beans.User;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/reponse")
public class ReponseController {

    private final Environment env;
    private final RestTemplate restTemplate;
    private final String baseURLRest1;

    @Autowired
    public ReponseController(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
        this.baseURLRest1 = env.getProperty("rest1.url") + "/reponse";
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Reponse>> getAll() {
        ResponseEntity<Reponse[]> response = restTemplate.getForEntity(baseURLRest1, Reponse[].class);
        List<Reponse> reponseList = new ArrayList<>(Arrays.asList(response.getBody()));

        return ResponseEntity.ok(reponseList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer pkReponse) {
        ResponseEntity<Reponse> response = restTemplate.getForEntity(baseURLRest1 + "/get/" + pkReponse, Reponse.class);

        // Vérifie si la requête est réussie
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(HttpSession session, @RequestParam String nom, @RequestParam Boolean correct, @RequestParam Integer pkQuestion) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("username");
        String username = user.getUsername();
        if (username != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("nom", nom);
            params.put("correct", String.valueOf(correct));
            params.put("pkQuestion", String.valueOf(pkQuestion));
            params.put("username", username);

            ResponseEntity<Reponse> response = restTemplate.getForEntity(baseURLRest1 + "/add", Reponse.class);

            // Vérifie si la requête est réussie
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour l'ajout d'une reponse.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(HttpSession session, @RequestParam Integer pkReponse, @RequestParam String nom, @RequestParam Boolean correct) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("username");
        String username = user.getUsername();
        if (username != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("pkReponse", String.valueOf(pkReponse));
            params.put("nom", nom);
            params.put("correct", String.valueOf(correct));
            params.put("username", username);

            ResponseEntity<Reponse> response = restTemplate.getForEntity(baseURLRest1 + "/update", Reponse.class);

            // Vérifie si la requête est réussie
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la modifcation d'une reponse.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(HttpSession session, @RequestParam Integer pkReponse) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("username");
        String username = user.getUsername();
        if (username != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("pkReponse", String.valueOf(pkReponse));
            params.put("username", username);

            ResponseEntity<String> response = restTemplate.getForEntity(baseURLRest1 + "/delete", String.class);

            // Vérifie si la requête est réussie
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la suppression d'une reponse.", HttpStatus.FORBIDDEN);
        }
    }
}