package br.com.nutritionone.Nutrition_One.dto.Meals;

import br.com.nutritionone.Nutrition_One.dto.Meals.MealResponseDTO;
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
    private List<MealResponseDTO> meals;

    // Totais do dia
    private NutritionTotals dailyTotals;

    // Metas do usuário (do perfil)
    private UserTargets targets;

    // Progresso (% das metas)
    private Progress progress;
}