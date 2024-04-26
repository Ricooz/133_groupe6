package ch.richozm.youquizplay.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.richozm.youquizplay.services.UserQuizService;

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

    @PostMapping("/points")
    public @ResponseBody String contabilisePoints(Integer userId, Integer quizId, Integer points) {
        return userQuizService.contabilisePoints(userId, quizId, points);
    }
}