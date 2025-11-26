package br.com.nutritionone.Nutrition_One.dto.Meals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class NutritionTotals {
    // Macros
    private BigDecimal calories;
    private BigDecimal protein;
    private BigDecimal carbohydrate;
    private BigDecimal fat;
    private BigDecimal fiber;

    // Micros principais
    private BigDecimal calcium;
    private BigDecimal iron;
    private BigDecimal sodium;
    private BigDecimal vitaminC;
}
