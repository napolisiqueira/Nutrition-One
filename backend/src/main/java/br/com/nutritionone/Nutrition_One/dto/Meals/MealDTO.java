package br.com.nutritionone.Nutrition_One.dto.Meals;

import br.com.nutritionone.Nutrition_One.model.Meal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// DTO para criar/atualizar refeição
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealDTO {
    private String name;
    private Meal.MealType type;
    private LocalDate date;
    private String notes;
    private List<MealFoodDTO> foods;
}