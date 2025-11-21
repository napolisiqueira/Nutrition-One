package br.com.nutritionone.Nutrition_One.dto.Meals;

import br.com.nutritionone.Nutrition_One.model.Meal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class MealsResponseDTO {
    private Long id;
    private String name;
    private Meal.MealType type;
    private LocalDate date;
    private String notes;

    // Lista de alimentos com valores calculados
    private List<MealFoodResponseDTO> foods;

    // Totais da refeição
    private NutritionTotals totals;
}