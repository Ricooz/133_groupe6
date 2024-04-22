package ch.richozm.youquizplay.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.richozm.youquizplay.service.UserQuizService;

@RestController
@RequestMapping("/userquiz")
public class UserQuizCtrl {

    private UserQuizService userQuizService;

    @Autowired
    public UserQuizCtrl(UserQuizService userQuizService) {
        this.userQuizService = userQuizService;
    }

    @GetMapping("/")
    public String getNothing() {
        return "Test";
    }
}