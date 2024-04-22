package ch.richozm.youquizplay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.richozm.youquizplay.model.User;
import ch.richozm.youquizplay.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public String addUser(String username, String password) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            return "Un utilisateur avec ce nom d'utilisateur existe déjà.";
        }

        User user = new User();
        userRepository.findByUsername(username);
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return "User " + username + " sauvegardé avec succès !";
    }

    @Transactional
    public Boolean checkLogin(String username, String password) {
        return false;
    }
}
