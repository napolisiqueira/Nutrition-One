package br.com.nutritionone.Nutrition_One.repository;

import br.com.nutritionone.Nutrition_One.model.User;
import br.com.nutritionone.Nutrition_One.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);
    Optional<UserProfile> findByUserId(UUID userId);
    boolean existsByUserId(UUID userId);
}