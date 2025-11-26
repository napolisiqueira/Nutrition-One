package br.com.nutritionone.Nutrition_One.controller;

import br.com.nutritionone.Nutrition_One.security.JWTCreator;
import br.com.nutritionone.Nutrition_One.security.JWTObject;
import br.com.nutritionone.Nutrition_One.security.SecurityConfig;
import br.com.nutritionone.Nutrition_One.dto.Login;
import br.com.nutritionone.Nutrition_One.dto.Sessao;
import br.com.nutritionone.Nutrition_One.model.User;
import br.com.nutritionone.Nutrition_One.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            User user = userRepository.findByUsername(login.getUsername());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuário não encontrado");
            }

            boolean passwordOk = encoder.matches(login.getPassword(), user.getPassword());

            if (!passwordOk) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Senha inválida");
            }

            Sessao sessao = new Sessao();
            sessao.setUsername(user.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setSubject(user.getUsername());
            jwtObject.setIssueAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION));
            jwtObject.setRoles(user.getRoles());

            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));

            return ResponseEntity.ok(sessao);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao tentar fazer login: " + e.getMessage());
        }
    }
}