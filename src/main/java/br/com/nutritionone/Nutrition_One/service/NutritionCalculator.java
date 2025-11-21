package br.com.nutritionone.Nutrition_One.service;

import br.com.nutritionone.Nutrition_One.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class NutritionCalculator {

    /**
     * Calcula a Taxa Metabólica Basal (TMB/BMR) usando a fórmula de Mifflin-St Jeor
     * É a mais precisa e atualizada
     *
     * Homens: TMB = (10 × peso em kg) + (6,25 × altura em cm) - (5 × idade em anos) + 5
     * Mulheres: TMB = (10 × peso em kg) + (6,25 × altura em cm) - (5 × idade em anos) - 161
     */
    public double calculateBMR(Double weight, Double height, Integer age, UserProfile.Gender gender) {
        if (weight == null || height == null || age == null || gender == null) {
            throw new IllegalArgumentException("Dados insuficientes para calcular TMB");
        }

        double bmr = (10 * weight) + (6.25 * height) - (5 * age);

        switch (gender) {
            case MALE:
                bmr += 5;
                break;
            case FEMALE:
                bmr -= 161;
                break;
            case OTHER:
                // Usa média entre os dois
                bmr -= 78;
                break;
        }

        return Math.round(bmr * 100.0) / 100.0; // 2 casas decimais
    }

    /**
     * Calcula o Gasto Energético Total Diário (GET/TDEE)
     * TDEE = TMB × Fator de Atividade
     */
    public double calculateTDEE(double bmr, UserProfile.ActivityLevel activityLevel) {
        if (activityLevel == null) {
            throw new IllegalArgumentException("Nível de atividade não definido");
        }

        double multiplier = switch (activityLevel) {
            case SEDENTARY -> 1.2;      // Sedentário
            case LIGHT -> 1.375;         // Levemente ativo
            case MODERATE -> 1.55;       // Moderadamente ativo
            case VERY_ACTIVE -> 1.725;   // Muito ativo
            case EXTRA_ACTIVE -> 1.9;    // Extremamente ativo
        };

        return Math.round(bmr * multiplier * 100.0) / 100.0;
    }

    /**
     * Calcula as calorias alvo baseado no objetivo
     */
    public double calculateTargetCalories(double tdee, UserProfile.Goal goal) {
        if (goal == null) {
            return tdee; // Manutenção por padrão
        }

        return switch (goal) {
            case LOSE_WEIGHT -> Math.round((tdee - 500) * 100.0) / 100.0;  // Déficit de 500 kcal
            case MAINTAIN -> Math.round(tdee * 100.0) / 100.0;             // Manutenção
            case GAIN_WEIGHT -> Math.round((tdee + 500) * 100.0) / 100.0;  // Superávit de 500 kcal
            case GAIN_MUSCLE -> Math.round((tdee + 300) * 100.0) / 100.0;  // Superávit moderado de 300 kcal
        };
    }

    public MacroNutrients calculateMacros(double targetCalories, Double weight, UserProfile.Goal goal) {
        double protein, fat, carbs;

        protein = switch (goal) {
            case LOSE_WEIGHT -> weight * 2.0;   // 2g/kg para preservar massa muscular
            case GAIN_MUSCLE -> weight * 2.2;   // 2.2g/kg para hipertrofia
            case GAIN_WEIGHT -> weight * 1.8;   // 1.8g/kg
            case MAINTAIN -> weight * 1.6;      // 1.6g/kg
        };

        fat = (targetCalories * 0.30) / 9;

        double proteinCalories = protein * 4;  // 1g proteína = 4 kcal
        double fatCalories = fat * 9;          // 1g gordura = 9 kcal
        double remainingCalories = targetCalories - proteinCalories - fatCalories;
        carbs = remainingCalories / 4;         // 1g carbo = 4 kcal

        return new MacroNutrients(
                Math.round(protein * 10.0) / 10.0,
                Math.round(carbs * 10.0) / 10.0,
                Math.round(fat * 10.0) / 10.0
        );
    }

    public double calculateBMI(Double weight, Double height) {
        if (weight == null || height == null) {
            throw new IllegalArgumentException("Peso e altura são necessários para calcular IMC");
        }

        double heightInMeters = height / 100.0;
        return Math.round((weight / (heightInMeters * heightInMeters)) * 100.0) / 100.0;
    }

    public Double calculateLeanMass(Double weight, Double bodyFatPercentage) {
        if (weight == null || bodyFatPercentage == null) {
            return null;
        }

        return Math.round((weight - (weight * bodyFatPercentage / 100.0)) * 100.0) / 100.0;
    }

    public record MacroNutrients(double protein, double carbs, double fat) {}
}