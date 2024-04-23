package ch.richozm.youquizplay.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.richozm.youquizplay.service.UserService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/user")
public class UserCtrl {

    private UserService userService;

    @Autowired
    public UserCtrl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/login")
    public @ResponseBody Boolean checkLogin(@RequestParam String username, @RequestParam String password) {
        return userService.checkLogin(username, password);
    }

    @PostMapping(path = "/addUser")
    public @ResponseBody String addUser(@RequestParam String username, @RequestParam String password) {
        return userService.addUser(username, password);
    }
}