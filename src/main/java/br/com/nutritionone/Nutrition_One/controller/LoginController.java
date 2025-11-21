package br.com.nutritionone.Nutrition_One.controller;


import br.com.nutritionone.Nutrition_One.security.JWTCreator;
import br.com.nutritionone.Nutrition_One.security.JWTObject;
import br.com.nutritionone.Nutrition_One.security.SecurityConfig;
import br.com.nutritionone.Nutrition_One.dto.Login;
import br.com.nutritionone.Nutrition_One.dto.Sessao;
import br.com.nutritionone.Nutrition_One.model.User;
import br.com.nutritionone.Nutrition_One.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public Sessao login(@RequestBody Login login) {
        User user = userRepository.findByUsername(login.getUsername());
        if (user != null) {
            boolean passwordOk = encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new RuntimeException("Senha invalida para o login " + login.getPassword());
            }

            Sessao sessao = new Sessao();
            sessao.setUsername(user.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssueAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION));
            jwtObject.setRoles(user.getRoles());
            sessao.setToken(JWTCreator.create(securityConfig.PREFIX, securityConfig.KEY, jwtObject));
            return sessao;
        } else {
            throw new RuntimeException("Erro ao tentar fazer login.");
        }
    }
}
