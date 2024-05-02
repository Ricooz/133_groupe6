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

import ch.emf.youquiz.beans.Reponse;
import ch.emf.youquiz.beans.User;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins =  {"https://morisettid.emf-informatique.ch", "https://richozm.emf-informatique.ch"}, allowCredentials = "true")
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
        try {
            ResponseEntity<Reponse> response = restTemplate.getForEntity(baseURLRest1 + "/get/" + pkReponse, Reponse.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(HttpSession session, @RequestParam String nom, @RequestParam Boolean correct, @RequestParam Integer pkQuestion) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();

            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("nom", nom);
            params.add("correct", String.valueOf(correct));
            params.add("pkQuestion", String.valueOf(pkQuestion));
            params.add("username", username);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            try {
                ResponseEntity<Reponse> response = restTemplate.postForEntity(baseURLRest1 + "/add", requestEntity, Reponse.class);
                return ResponseEntity.ok(response.getBody());
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour l'ajout d'une reponse.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(HttpSession session, @RequestParam Integer pkReponse, @RequestParam String nom, @RequestParam Boolean correct) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();

            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("pkReponse", String.valueOf(pkReponse));
            params.add("nom", nom);
            params.add("correct", String.valueOf(correct));
            params.add("username", username);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            try {
                ResponseEntity<Reponse> response = restTemplate.exchange(baseURLRest1 + "/update", HttpMethod.PUT, requestEntity, Reponse.class);
                return ResponseEntity.ok(response.getBody());
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la modifcation d'une reponse.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(HttpSession session, @RequestParam Integer pkReponse) {
        // Vérifie si l'utilisateur est connecté
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURLRest1 + "/delete")
                .queryParam("pkReponse", pkReponse)
                .queryParam("username", username);

            try {
                ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, null, String.class);
                
                return ResponseEntity.ok(response.getBody());
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
        } else {
            return new ResponseEntity<>("Connexion nécessaire pour la suppression d'une reponse.", HttpStatus.FORBIDDEN);
        }
    }
}