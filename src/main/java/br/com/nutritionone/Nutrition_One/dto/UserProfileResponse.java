package br.com.nutritionone.Nutrition_One.dto;

import br.com.nutritionone.Nutrition_One.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String username;

    // Dados Físicos
    private Double weight;
    private Double height;
    private Integer age;
    private UserProfile.Gender gender;
    private LocalDate birthDate;

    // Objetivos
    private UserProfile.Goal goal;
    private UserProfile.ActivityLevel activityLevel;

    // Cálculos (gerados automaticamente)
    private Double bmr;           // Taxa Metabólica Basal
    private Double tdee;          // Gasto Energético Total
    private Double targetCalories;
    private Double targetProtein;
    private Double targetCarbs;
    private Double targetFat;

    // Métricas
    private Double bmi;
    private Double bodyFatPercentage;
    private Double leanMass;

    // Controle
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserProfileResponse fromEntity(UserProfile profile) {
        UserProfileResponse response = new UserProfileResponse();
        response.setId(profile.getId());
        response.setUsername(profile.getUser().getUsername());
        response.setWeight(profile.getWeight());
        response.setHeight(profile.getHeight());
        response.setAge(profile.getAge());
        response.setGender(profile.getGender());
        response.setBirthDate(profile.getBirthDate());
        response.setGoal(profile.getGoal());
        response.setActivityLevel(profile.getActivityLevel());
        response.setBmr(profile.getBmr());
        response.setTdee(profile.getTdee());
        response.setTargetCalories(profile.getTargetCalories());
        response.setTargetProtein(profile.getTargetProtein());
        response.setTargetCarbs(profile.getTargetCarbs());
        response.setTargetFat(profile.getTargetFat());
        response.setBmi(profile.getBmi());
        response.setBodyFatPercentage(profile.getBodyFatPercentage());
        response.setLeanMass(profile.getLeanMass());
        response.setCreatedAt(profile.getCreatedAt());
        response.setUpdatedAt(profile.getUpdatedAt());
        return response;
    }
}