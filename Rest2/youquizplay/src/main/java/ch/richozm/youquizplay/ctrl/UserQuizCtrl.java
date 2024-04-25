package ch.richozm.youquizplay.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.richozm.youquizplay.service.UserQuizService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/userquiz")
public class UserQuizCtrl {

    private UserQuizService userQuizService;

    @Autowired
    public UserQuizCtrl(UserQuizService userQuizService) {
        this.userQuizService = userQuizService;
    }

    @PostMapping("/likeQuiz")
    public void likeQuiz(@RequestParam String username, @RequestParam String quizTitle, @RequestParam Boolean liked) {
        userQuizService.likeQuiz(username, quizTitle, liked);
    }

    @PostMapping("/unLikeQuiz")
    public void unLikeQuiz(@RequestParam Integer userId, @RequestParam Integer quizId, @RequestParam Boolean liked) {
        userQuizService.unLikeQuiz(userId, quizId, liked);
    }

    @PostMapping("/validateQuiz")
    public void validateQuiz(@RequestParam Integer quizId) {
        userQuizService.validateQuiz(quizId);
    }
}