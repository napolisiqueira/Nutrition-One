package br.com.nutritionone.Nutrition_One.service;

import br.com.nutritionone.Nutrition_One.model.Food;
import br.com.nutritionone.Nutrition_One.model.MealFood;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class NutritionCalculatorService {

    public void calculateNutrients(MealFood mealFood) {
        Food food = mealFood.getFood();
        BigDecimal quantity = mealFood.getQuantity();
        BigDecimal divisor = new BigDecimal("100");

        // MACRONUTRIENTES
        mealFood.setCalculatedCalories(
                calculate(food.getCalories(), quantity, divisor)
        );

        mealFood.setCalculatedProtein(
                calculate(food.getProtein(), quantity, divisor)
        );

        mealFood.setCalculatedCarbohydrate(
                calculate(food.getCarbohydrate(), quantity, divisor)
        );

        mealFood.setCalculatedFat(
                calculate(food.getTotalFat(), quantity, divisor)
        );

        mealFood.setCalculatedFiber(
                calculate(food.getFiber(), quantity, divisor)
        );

        // MICRONUTRIENTES
        if (food.getCalcium() != null) {
            mealFood.setCalculatedCalcium(
                    calculate(food.getCalcium(), quantity, divisor)
            );
        }

        if (food.getIron() != null) {
            mealFood.setCalculatedIron(
                    calculate(food.getIron(), quantity, divisor)
            );
        }

        if (food.getSodium() != null) {
            mealFood.setCalculatedSodium(
                    calculate(food.getSodium(), quantity, divisor)
            );
        }

        if (food.getVitaminC() != null) {
            mealFood.setCalculatedVitaminC(
                    calculate(food.getVitaminC(), quantity, divisor)
            );
        }
    }

    private BigDecimal calculate(BigDecimal valuePer100g, BigDecimal quantity, BigDecimal divisor) {
        if (valuePer100g == null) {
            return BigDecimal.ZERO;
        }

        return valuePer100g
                .multiply(quantity)
                .divide(divisor, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePercentage(BigDecimal current, BigDecimal target) {
        if (target == null || target.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return current
                .multiply(new BigDecimal("100"))
                .divide(target, 1, RoundingMode.HALF_UP);
    }
}