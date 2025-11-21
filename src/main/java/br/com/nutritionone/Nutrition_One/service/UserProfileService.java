package br.com.nutritionone.Nutrition_One.service;

import br.com.nutritionone.Nutrition_One.dto.UserProfileDTO;
import br.com.nutritionone.Nutrition_One.model.User;
import br.com.nutritionone.Nutrition_One.model.UserProfile;
import br.com.nutritionone.Nutrition_One.repository.UserProfileRepository;
import br.com.nutritionone.Nutrition_One.repository.UserRepository;
import br.com.nutritionone.Nutrition_One.service.NutritionCalculator.MacroNutrients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NutritionCalculator calculator;

        @Transactional
    public UserProfile createProfile(UUID userId, UserProfileDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (profileRepository.existsByUserId(userId)) {
            throw new RuntimeException("Usuário já possui um perfil");
        }

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        updateProfileFields(profile, dto);
        calculateAndSetNutrition(profile);

        return profileRepository.save(profile);
    }

    /**
     * Busca o perfil do usuário
     */
    public UserProfile getProfile(UUID userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
    }

    /**
     * Atualiza TODO o perfil (substitui todos os dados)
     */
    @Transactional
    public UserProfile updateFullProfile(UUID userId, UserProfileDTO dto) {
        UserProfile profile = getProfile(userId);
        updateProfileFields(profile, dto);
        calculateAndSetNutrition(profile);
        return profileRepository.save(profile);
    }

    /**
     * Atualiza campos PARCIAIS (apenas os que foram enviados)
     */
    @Transactional
    public UserProfile updatePartialProfile(UUID userId, UserProfileDTO dto) {
        UserProfile profile = getProfile(userId);
        boolean needsRecalculation = false;

        // Atualiza apenas os campos não-nulos
        if (dto.getWeight() != null) {
            profile.setWeight(dto.getWeight());
            needsRecalculation = true;
        }
        if (dto.getHeight() != null) {
            profile.setHeight(dto.getHeight());
            needsRecalculation = true;
        }
        if (dto.getAge() != null) {
            profile.setAge(dto.getAge());
            needsRecalculation = true;
        }
        if (dto.getGender() != null) {
            profile.setGender(dto.getGender());
            needsRecalculation = true;
        }
        if (dto.getBirthDate() != null) {
            profile.setBirthDate(dto.getBirthDate());
        }
        if (dto.getGoal() != null) {
            profile.setGoal(dto.getGoal());
            needsRecalculation = true;
        }
        if (dto.getActivityLevel() != null) {
            profile.setActivityLevel(dto.getActivityLevel());
            needsRecalculation = true;
        }
        if (dto.getBodyFatPercentage() != null) {
            profile.setBodyFatPercentage(dto.getBodyFatPercentage());
            needsRecalculation = true;
        }

        // Recalcula apenas se algo relevante mudou
        if (needsRecalculation) {
            calculateAndSetNutrition(profile);
        }

        return profileRepository.save(profile);
    }

    /**
     * Deleta o perfil do usuário
     */
    @Transactional
    public void deleteProfile(UUID userId) {
        UserProfile profile = getProfile(userId);
        profileRepository.delete(profile);
    }

    /**
     * Atualiza todos os campos do perfil a partir do DTO
     */
    private void updateProfileFields(UserProfile profile, UserProfileDTO dto) {
        if (dto.getWeight() != null) profile.setWeight(dto.getWeight());
        if (dto.getHeight() != null) profile.setHeight(dto.getHeight());
        if (dto.getAge() != null) profile.setAge(dto.getAge());
        if (dto.getGender() != null) profile.setGender(dto.getGender());
        if (dto.getBirthDate() != null) profile.setBirthDate(dto.getBirthDate());
        if (dto.getGoal() != null) profile.setGoal(dto.getGoal());
        if (dto.getActivityLevel() != null) profile.setActivityLevel(dto.getActivityLevel());
        if (dto.getBodyFatPercentage() != null) profile.setBodyFatPercentage(dto.getBodyFatPercentage());
    }

    private void calculateAndSetNutrition(UserProfile profile) {
        // TMB (Taxa Metabólica Basal)
        double bmr = calculator.calculateBMR(
                profile.getWeight(),
                profile.getHeight(),
                profile.getAge(),
                profile.getGender()
        );
        profile.setBmr(bmr);

        // GET/TDEE (Gasto Energético Total Diário)
        double tdee = calculator.calculateTDEE(bmr, profile.getActivityLevel());
        profile.setTdee(tdee);

        // Calorias alvo baseado no objetivo
        double targetCalories = calculator.calculateTargetCalories(tdee, profile.getGoal());
        profile.setTargetCalories(targetCalories);

        // Macronutrientes
        MacroNutrients macros = calculator.calculateMacros(
                targetCalories,
                profile.getWeight(),
                profile.getGoal()
        );
        profile.setTargetProtein(macros.protein());
        profile.setTargetCarbs(macros.carbs());
        profile.setTargetFat(macros.fat());

        // IMC
        double bmi = calculator.calculateBMI(profile.getWeight(), profile.getHeight());
        profile.setBmi(bmi);

        // Massa magra (se tiver % de gordura)
        if (profile.getBodyFatPercentage() != null) {
            Double leanMass = calculator.calculateLeanMass(
                    profile.getWeight(),
                    profile.getBodyFatPercentage()
            );
            profile.setLeanMass(leanMass);
        }
    }
}