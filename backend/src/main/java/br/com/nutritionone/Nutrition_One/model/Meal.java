package br.com.nutritionone.Nutrition_One.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name; // Ex: "Café da Manhã", "Almoço", "Jantar"

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private MealType type; // BREAKFAST, LUNCH, DINNER, SNACK, etc

    @Column(nullable = false)
    private LocalDate date; // Data da refeição

    @Column(length = 500)
    private String notes; // Observações do usuário

    // Relacionamento com alimentos
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealFood> foods = new ArrayList<>();

    // Totais calculados (soma de todos os alimentos)
    @Column(precision = 10, scale = 2)
    private BigDecimal totalCalories;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalProtein;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalCarbohydrate;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalFat;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalFiber;

    // Micronutrientes totais
    @Column(precision = 10, scale = 3)
    private BigDecimal totalCalcium;

    @Column(precision = 10, scale = 3)
    private BigDecimal totalIron;

    @Column(precision = 10, scale = 3)
    private BigDecimal totalSodium;

    @Column(precision = 10, scale = 3)
    private BigDecimal totalVitaminC;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Enums
    public enum MealType {
        BREAKFAST,      // Café da Manhã
        MORNING_SNACK,  // Lanche da Manhã
        LUNCH,          // Almoço
        AFTERNOON_SNACK,// Lanche da Tarde
        DINNER,         // Jantar
        EVENING_SNACK,  // Ceia
        OTHER           // Outro
    }
}
