package br.com.nutritionone.Nutrition_One.repository;

import br.com.nutritionone.Nutrition_One.model.Food;
import br.com.nutritionone.Nutrition_One.model.Meal;
import br.com.nutritionone.Nutrition_One.model.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// Repository de Alimentos
public interface FoodRepository extends JpaRepository<Food, Long> {

    // Buscar por nome (parcial, case insensitive)
    @Query("SELECT f FROM Food f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Food> searchByName(@Param("name") String name);

    // Buscar por categoria
    List<Food> findByCategory(String category);

    // Buscar alimentos verificados
    List<Food> findByVerifiedTrue();

    // Buscar por fonte (TACO, USDA, etc)
    List<Food> findBySource(String source);
}
