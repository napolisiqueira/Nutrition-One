package br.com.nutritionone.Nutrition_One.dto.Meals;

import br.com.nutritionone.Nutrition_One.dto.Meals.MealsResponseDTO;
import br.com.nutritionone.Nutrition_One.dto.Meals.NutritionTotals;
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
class DailyMealSummaryDTO {
    private LocalDate date;
    private List<MealsResponseDTO> meals;

    private NutritionTotals dailyTotals;

    private UserTargets targets;

    private Progress progress;
}