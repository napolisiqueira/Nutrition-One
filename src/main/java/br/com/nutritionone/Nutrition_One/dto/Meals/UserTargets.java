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
class UserTargets {
    private BigDecimal targetCalories;
    private BigDecimal targetProtein;
    private BigDecimal targetCarbs;
    private BigDecimal targetFat;
}
