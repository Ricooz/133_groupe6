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
            return new ResponseEntity<>("Pk reponse invalide", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(@RequestParam String nom, @RequestParam Boolean correct, @RequestParam Integer pkQuestion) {
        ReponseDTO reponseDTO = reponseService.addReponse(nom, correct, pkQuestion);
        if (reponseDTO != null) {
            return new ResponseEntity<>(reponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("PK question invalide", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestParam Integer pkReponse,  String nom, Boolean correct) {
        ReponseDTO reponseDTO = reponseService.updateReponse(pkReponse, nom, correct);
        if (reponseDTO != null) {
            return new ResponseEntity<>(reponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("PK reponse invalide", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam Integer pkReponse) {
        if (reponseService.deleteReponse(pkReponse)) {
            return new ResponseEntity<>("Reponse supprimée avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("PK reponse invalide", HttpStatus.BAD_REQUEST);
        }
    }
}