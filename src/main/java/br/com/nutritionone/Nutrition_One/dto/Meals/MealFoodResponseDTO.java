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
class MealFoodResponseDTO {
    private Long id;
    private Long foodId;
    private String foodName;
    private String foodCategory;
    private BigDecimal quantity;
    private String notes;

    // Valores calculados baseado na quantidade
    private BigDecimal calories;
    private BigDecimal protein;
    private BigDecimal carbohydrate;
    private BigDecimal fat;
    private BigDecimal fiber;

    // Principais micronutrientes
    private BigDecimal calcium;
    private BigDecimal iron;
    private BigDecimal sodium;
    private BigDecimal vitaminC;
}