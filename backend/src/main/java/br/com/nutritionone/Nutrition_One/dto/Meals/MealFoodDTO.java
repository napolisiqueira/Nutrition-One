package br.com.nutritionone.Nutrition_One.dto.Meals;

import br.com.nutritionone.Nutrition_One.model.Meal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealFoodDTO {
    private Long foodId;           // ID do alimento da tabela TACO
    private BigDecimal quantity;   // Quantidade em gramas
    private String notes;          // Ex: "Grelhado", "Com azeite"
}