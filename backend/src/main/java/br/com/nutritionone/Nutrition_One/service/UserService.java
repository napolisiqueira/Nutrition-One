package br.com.nutritionone.Nutrition_One.service;

import br.com.nutritionone.Nutrition_One.model.User;
import br.com.nutritionone.Nutrition_One.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void createUser(User user) {
        // Verifica se username já existe
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username já existe");
        }

        // Encripta a senha
        String pass = user.getPassword();
        user.setPassword(encoder.encode(pass));

        // Define role padrão se não houver - CORRIGIDO
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());  // ✅ Cria lista nova
        }

        if (user.getRoles().isEmpty()) {
            user.getRoles().add("USER");  // ✅ Agora pode adicionar
        }

        userRepository.save(user);
    }
}