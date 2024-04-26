package ch.richozm.youquizplay.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.richozm.youquizplay.model.User;
import ch.richozm.youquizplay.services.UserService;

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

    @PostMapping(path = "/login")
    public ResponseEntity<?> checkLogin(@RequestParam String username, @RequestParam String password) {
        User user = userService.checkLogin(username, password);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Nom d'utilisateur ou mot passe invalide.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.addUser(username, password);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Nom d'utilisateur déja utilisé.", HttpStatus.CONFLICT);
        }
    }
}