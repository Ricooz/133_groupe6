package ch.richozm.youquizplay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.richozm.youquizplay.model.User;
import ch.richozm.youquizplay.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User addUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) { // Si ce nom d'utilisateur existe déja
            return null;
        }

        String hashedPassword = passwordEncoder.encode(password);

        user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User checkLogin(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            } else {
                user = null;
            }
        }

        return user;
    }
}