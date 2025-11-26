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
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name; // Ex: "Arroz branco cozido"

    @Column(length = 100)
    private String category; // Ex: "Cereais", "Carnes", "Frutas"

    // Valores por 100g do alimento

    // MACRONUTRIENTES (em gramas)
    @Column(precision = 8, scale = 2)
    private BigDecimal calories; // kcal

    @Column(precision = 8, scale = 2)
    private BigDecimal protein; // proteína (g)

    @Column(precision = 8, scale = 2)
    private BigDecimal carbohydrate; // carboidrato (g)

    @Column(precision = 8, scale = 2)
    private BigDecimal fiber; // fibra (g)

    @Column(precision = 8, scale = 2)
    private BigDecimal totalFat; // gordura total (g)

    @Column(precision = 8, scale = 2)
    private BigDecimal saturatedFat; // gordura saturada (g)

    @Column(precision = 8, scale = 2)
    private BigDecimal transFat; // gordura trans (g)

    @Column(precision = 8, scale = 2)
    private BigDecimal monounsaturatedFat; // gordura monoinsaturada (g)

    @Column(precision = 8, scale = 2)
    private BigDecimal polyunsaturatedFat; // gordura poli-insaturada (g)

    // MICRONUTRIENTES (em mg ou mcg)

    @Column(precision = 10, scale = 3)
    private BigDecimal calcium; // cálcio (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal iron; // ferro (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal sodium; // sódio (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal magnesium; // magnésio (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal phosphorus; // fósforo (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal potassium; // potássio (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal zinc; // zinco (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal vitaminC; // vitamina C (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal vitaminB6; // vitamina B6 (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal vitaminB12; // vitamina B12 (mcg)

    @Column(precision = 10, scale = 3)
    private BigDecimal vitaminA; // vitamina A (mcg)

    @Column(precision = 10, scale = 3)
    private BigDecimal vitaminD; // vitamina D (mcg)

    @Column(precision = 10, scale = 3)
    private BigDecimal vitaminE; // vitamina E (mg)

    @Column(precision = 10, scale = 3)
    private BigDecimal cholesterol; // colesterol (mg)

    // Metadados
    @Column(length = 50)
    private String source; // Ex: "TACO", "USDA", "Manual"

    @Column(columnDefinition = "TEXT")
    private String description; // Descrição adicional

    private Boolean verified; // Se foi verificado por admin
}