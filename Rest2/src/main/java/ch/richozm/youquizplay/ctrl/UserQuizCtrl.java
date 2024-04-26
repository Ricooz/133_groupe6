package ch.richozm.youquizplay.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/like")
    public @ResponseBody String likeQuiz(@RequestParam Integer userId, @RequestParam Integer quizId) {
        return userQuizService.likeQuiz(userId, quizId);
    }

    @GetMapping("/points/get")
    public @ResponseBody String getPoints(@RequestParam String username) {
        return userQuizService.getPoints(username);
    }

    @PostMapping("/points/add")
    public @ResponseBody String addPoints(@RequestParam String username, @RequestParam Integer quizId, @RequestParam Integer points) {
        return userQuizService.contabilisePoints(userId, quizId, points);
    }

    @GetMapping("/likes/{quizId}")
    public @ResponseBody Integer getNbrLike(@PathVariable("quizId") Integer quizId) {
        return userQuizService.getNbrLikes(quizId);
    }
}