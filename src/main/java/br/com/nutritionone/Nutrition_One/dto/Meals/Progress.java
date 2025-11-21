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
class Progress {
    private BigDecimal caloriesPercentage;
    private BigDecimal proteinPercentage;
    private BigDecimal carbsPercentage;
    private BigDecimal fatPercentage;
}