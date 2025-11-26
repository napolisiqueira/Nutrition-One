package br.com.nutritionone.Nutrition_One.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meal_foods")
public class MealFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity; // Quantidade em gramas

    // Valores calculados com base na quantidade
    // Ex: Se 100g de arroz = 130kcal, e quantidade = 200g → calculatedCalories = 260kcal

    @Column(precision = 10, scale = 2)
    private BigDecimal calculatedCalories;

    @Column(precision = 10, scale = 2)
    private BigDecimal calculatedProtein;

    @Column(precision = 10, scale = 2)
    private BigDecimal calculatedCarbohydrate;

    @Column(precision = 10, scale = 2)
    private BigDecimal calculatedFat;

    @Column(precision = 10, scale = 2)
    private BigDecimal calculatedFiber;

    @Column(precision = 10, scale = 3)
    private BigDecimal calculatedCalcium;

    @Column(precision = 10, scale = 3)
    private BigDecimal calculatedIron;

    @Column(precision = 10, scale = 3)
    private BigDecimal calculatedSodium;

    @Column(precision = 10, scale = 3)
    private BigDecimal calculatedVitaminC;

    @Column(length = 200)
    private String notes; // Ex: "Com azeite", "Grelhado"
}