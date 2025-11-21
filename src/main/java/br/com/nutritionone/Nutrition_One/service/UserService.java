package br.com.nutritionone.Nutrition_One.service;

import br.com.nutritionone.Nutrition_One.model.User;
import br.com.nutritionone.Nutrition_One.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username já existe");
        }

        String pass = user.getPassword();
        user.setPassword(encoder.encode(pass));

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.getRoles().add("USER");
        }

        userRepository.save(user);
    }
}