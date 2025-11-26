package br.com.nutritionone.Nutrition_One.dto;

import br.com.nutritionone.Nutrition_One.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

    // Dados Físicos (podem ser atualizados individualmente)
    private Double weight;
    private Double height;
    private Integer age;
    private UserProfile.Gender gender;
    private LocalDate birthDate;

    // Objetivos
    private UserProfile.Goal goal;
    private UserProfile.ActivityLevel activityLevel;

    // Métricas opcionais
    private Double bodyFatPercentage;

    // Os valores calculados NÃO vêm no DTO de entrada
    // Eles são calculados automaticamente no backend
}