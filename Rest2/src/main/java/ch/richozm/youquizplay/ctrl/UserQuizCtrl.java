package ch.richozm.youquizplay.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.richozm.youquizplay.services.UserQuizService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/userquiz")
public class UserQuizCtrl {

    private UserQuizService userQuizService;

    @Autowired
    public UserQuizCtrl(UserQuizService userQuizService) {
        this.userQuizService = userQuizService;
    }

    @PostMapping("/like/{id}")
    public @ResponseBody String likeQuiz(@RequestParam Integer userId, @PathVariable("id") Integer quizId) {
        return userQuizService.likeQuiz(userId, quizId);
    }

    @GetMapping("/points/get")
    public ResponseEntity<String> getPoints(@RequestParam Integer userId, @RequestParam Integer quizId) {
        Integer points = userQuizService.getPoints(userId, quizId);
        if (points != -1) {
            return new ResponseEntity<>(String.valueOf(points), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Utilisateur non trouvé. Id de l'utilisateur fournie invalide.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/points/getAll")
    public ResponseEntity<String> getAllPoints(@RequestParam Integer userId) {
        Integer points = userQuizService.getAllPoints(userId);
        if (points != -1) {
            return new ResponseEntity<>(String.valueOf(points), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Utilisateur non trouvé. Id de l'utilisateur fournie invalide.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/points/set")
    public ResponseEntity<String> addPoints(@RequestParam Integer userId, @RequestParam Integer quizId, @RequestParam Integer points) {
        Boolean userExist = userQuizService.contabilisePoints(userId, quizId, points);
        if (userExist) {
            return new ResponseEntity<>("Points attribué avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Utilisateur non trouvé. Id de l'utilisateur fournie invalide.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/likes/{quizId}")
    public @ResponseBody Integer getNbrLike(@PathVariable("quizId") Integer quizId) {
        return userQuizService.getNbrLikes(quizId);
    }
}