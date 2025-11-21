package br.com.nutritionone.Nutrition_One.repository;

import br.com.nutritionone.Nutrition_One.model.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealFoodRepository extends JpaRepository<MealFood, Long> {

    List<MealFood> findByMealId(Long mealId);

    void deleteByMealId(Long mealId);
}
