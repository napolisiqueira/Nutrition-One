package br.com.nutritionone.Nutrition_One.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double height;

    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Goal goal;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ActivityLevel activityLevel;

    private Double bmr;
    private Double tdee;
    private Double targetCalories;
    private Double targetProtein;
    private Double targetCarbs;
    private Double targetFat;
    private Double bmi;
    private Double bodyFatPercentage;
    private Double leanMass;

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
    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum Goal {
        LOSE_WEIGHT,
        MAINTAIN,
        GAIN_WEIGHT,
        GAIN_MUSCLE
    }

    public enum ActivityLevel {
        SEDENTARY,
        LIGHT,
        MODERATE,
        VERY_ACTIVE,
        EXTRA_ACTIVE
    }
}