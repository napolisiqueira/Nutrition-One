package br.com.nutritionone.Nutrition_One.service;

import br.com.nutritionone.Nutrition_One.model.User;
import br.com.nutritionone.Nutrition_One.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    public void createUser(User user) {
        String pass = user.getPassword();
        user.setPassword(encoder.encode(pass));
        userRepository.save(user);
    }
}
