package br.com.nutritionone.Nutrition_One.repository;

import br.com.nutritionone.Nutrition_One.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username = (:username)")
    public User findByUsername(@Param("username") String username);

    boolean existsByUsername(String usename);
}
