package ch.richozm.youquizplay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        userRepository.findByUsername(username);
        user.setUsername(username);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return "User " + username + " sauvegardé avec succès !";
    }

    @Transactional
    public Boolean checkLogin(String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        User existingUsername = userRepository.findByUsername(username);
        if (existingUsername != null) {
            if (existingUsername.getPassword().equals(hashedPassword)) {
                return true;
            }
        }
        return false;
    }
}