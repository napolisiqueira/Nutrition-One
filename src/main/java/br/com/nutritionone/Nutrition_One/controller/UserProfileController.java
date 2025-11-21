package br.com.nutritionone.Nutrition_One.controller;

import br.com.nutritionone.Nutrition_One.dto.UserProfileDTO;
import br.com.nutritionone.Nutrition_One.dto.UserProfileResponse;
import br.com.nutritionone.Nutrition_One.model.User;
import br.com.nutritionone.Nutrition_One.model.UserProfile;
import br.com.nutritionone.Nutrition_One.repository.UserRepository;
import br.com.nutritionone.Nutrition_One.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody UserProfileDTO dto, Authentication authentication) {
        try {
            User user = getUserFromAuth(authentication);
            UserProfile profile = profileService.createProfile(user.getId(), dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserProfileResponse.fromEntity(profile));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getProfile(Authentication authentication) {
        try {
            User user = getUserFromAuth(authentication);
            UserProfile profile = profileService.getProfile(user.getId());
            return ResponseEntity.ok(UserProfileResponse.fromEntity(profile));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateFullProfile(@RequestBody UserProfileDTO dto, Authentication authentication) {
        try {
            User user = getUserFromAuth(authentication);
            UserProfile profile = profileService.updateFullProfile(user.getId(), dto);
            return ResponseEntity.ok(UserProfileResponse.fromEntity(profile));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<?> updatePartialProfile(@RequestBody UserProfileDTO dto, Authentication authentication) {
        try {
            User user = getUserFromAuth(authentication);
            UserProfile profile = profileService.updatePartialProfile(user.getId(), dto);
            return ResponseEntity.ok(UserProfileResponse.fromEntity(profile));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProfile(Authentication authentication) {
        try {
            User user = getUserFromAuth(authentication);
            profileService.deleteProfile(user.getId());
            return ResponseEntity.ok("Perfil deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    private User getUserFromAuth(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }

    private record WeightUpdateDTO(Double weight) {}
    private record GoalUpdateDTO(UserProfile.Goal goal) {}
}