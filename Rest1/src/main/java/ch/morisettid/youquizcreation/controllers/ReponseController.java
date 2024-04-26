package ch.morisettid.youquizcreation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

import ch.morisettid.youquizcreation.dto.ReponseDTO;
import ch.morisettid.youquizcreation.exceptions.IdNotFoundException;
import ch.morisettid.youquizcreation.exceptions.UnauthorizedUserException;
import ch.morisettid.youquizcreation.services.ReponseService;

@RestController
@RequestMapping("/reponse")
public class ReponseController {

    private final ReponseService reponseService;

    @Autowired
    public ReponseController(ReponseService reponseService) {
        this.reponseService = reponseService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<ReponseDTO>> getAll() {
        return new ResponseEntity<>(reponseService.findAllReponses(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer pkReponse) {
        ReponseDTO reponseDTO = reponseService.findReponse(pkReponse);
        if (reponseDTO != null) {
            return new ResponseEntity<>(reponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Pk reponse invalide", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(@RequestParam String nom, @RequestParam Boolean correct, @RequestParam Integer pkQuestion, @RequestParam String username) {
        try {
            return new ResponseEntity<>(reponseService.addReponse(nom, correct, pkQuestion, username), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestParam Integer pkReponse, @RequestParam String nom, Boolean correct, @RequestParam String username) {
        try {
            return new ResponseEntity<>(reponseService.updateReponse(pkReponse, nom, correct, username), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam Integer pkReponse, @RequestParam String username) {
        try {
            reponseService.deleteReponse(pkReponse, username);
            return new ResponseEntity<>("Réponse supprimée avec succès.", HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}