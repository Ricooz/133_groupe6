package ch.richozm.youquizplay.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.richozm.youquizplay.service.UserQuizService;
import ch.richozm.youquizplay.service.UserService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Ctrl {

    private UserService userService;
    private UserQuizService userQuizService;

    @Autowired
    public Ctrl(UserService userService, UserQuizService userQuizService) {
        this.userService = userService;
        this.userQuizService = userQuizService;
    }

    @GetMapping("/")
    public String getNothing() {
        return "Test";
    }

    @PostMapping(path = "/login")
    public @ResponseBody Boolean checkLogin(@RequestParam String username, @RequestParam String password) {
        return userService.checkLogin(username, password);
    }

    @PostMapping(path = "/addUser")
    public @ResponseBody String addUser(@RequestParam String username, @RequestParam String password) {
        return userService.addUser(username, password);
    }
}