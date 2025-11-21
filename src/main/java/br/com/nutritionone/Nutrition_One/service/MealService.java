package br.com.nutritionone.Nutrition_One.service;

import br.com.nutritionone.Nutrition_One.dto.Meals.MealDTO;
import br.com.nutritionone.Nutrition_One.dto.Meals.MealFoodDTO;
import br.com.nutritionone.Nutrition_One.model.*;
import br.com.nutritionone.Nutrition_One.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MealFoodRepository mealFoodRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NutritionCalculatorService nutritionCalculator;

    /**
     * Cria uma nova refeição com alimentos
     */
    @Transactional
    public Meal createMeal(UUID userId, MealDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Cria a refeição
        Meal meal = new Meal();
        meal.setUser(user);
        meal.setName(dto.getName());
        meal.setType(dto.getType());
        meal.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        meal.setNotes(dto.getNotes());

        // Salva a refeição primeiro
        meal = mealRepository.save(meal);

        // Adiciona os alimentos
        if (dto.getFoods() != null && !dto.getFoods().isEmpty()) {
            for (MealFoodDTO foodDto : dto.getFoods()) {
                addFoodToMeal(meal, foodDto);
            }
        }

        // Calcula totais
        calculateMealTotals(meal);

        return mealRepository.save(meal);
    }

    /**
     * Adiciona um alimento à refeição
     */
    @Transactional
    public Meal addFoodToMeal(Long mealId, MealFoodDTO foodDto) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada"));

        addFoodToMeal(meal, foodDto);
        calculateMealTotals(meal);

        return mealRepository.save(meal);
    }

    /**
     * Helper para adicionar alimento
     */
    private void addFoodToMeal(Meal meal, MealFoodDTO foodDto) {
        Food food = foodRepository.findById(foodDto.getFoodId())
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado"));

        MealFood mealFood = new MealFood();
        mealFood.setMeal(meal);
        mealFood.setFood(food);
        mealFood.setQuantity(foodDto.getQuantity());
        mealFood.setNotes(foodDto.getNotes());

        // Calcula os nutrientes baseado na quantidade
        nutritionCalculator.calculateNutrients(mealFood);

        meal.getFoods().add(mealFood);
    }

    /**
     * Remove um alimento da refeição
     */
    @Transactional
    public Meal removeFoodFromMeal(Long mealId, Long mealFoodId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada"));

        meal.getFoods().removeIf(mf -> mf.getId().equals(mealFoodId));
        mealFoodRepository.deleteById(mealFoodId);

        calculateMealTotals(meal);
        return mealRepository.save(meal);
    }

    /**
     * Atualiza a quantidade de um alimento
     */
    @Transactional
    public Meal updateFoodQuantity(Long mealId, Long mealFoodId, BigDecimal newQuantity) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada"));

        MealFood mealFood = meal.getFoods().stream()
                .filter(mf -> mf.getId().equals(mealFoodId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado na refeição"));

        mealFood.setQuantity(newQuantity);
        nutritionCalculator.calculateNutrients(mealFood);

        calculateMealTotals(meal);
        return mealRepository.save(meal);
    }

    /**
     * Calcula os totais nutricionais da refeição
     */
    private void calculateMealTotals(Meal meal) {
        BigDecimal totalCals = BigDecimal.ZERO;
        BigDecimal totalProt = BigDecimal.ZERO;
        BigDecimal totalCarbs = BigDecimal.ZERO;
        BigDecimal totalFat = BigDecimal.ZERO;
        BigDecimal totalFiber = BigDecimal.ZERO;
        BigDecimal totalCalcium = BigDecimal.ZERO;
        BigDecimal totalIron = BigDecimal.ZERO;
        BigDecimal totalSodium = BigDecimal.ZERO;
        BigDecimal totalVitC = BigDecimal.ZERO;

        for (MealFood mf : meal.getFoods()) {
            totalCals = totalCals.add(mf.getCalculatedCalories() != null ? mf.getCalculatedCalories() : BigDecimal.ZERO);
            totalProt = totalProt.add(mf.getCalculatedProtein() != null ? mf.getCalculatedProtein() : BigDecimal.ZERO);
            totalCarbs = totalCarbs.add(mf.getCalculatedCarbohydrate() != null ? mf.getCalculatedCarbohydrate() : BigDecimal.ZERO);
            totalFat = totalFat.add(mf.getCalculatedFat() != null ? mf.getCalculatedFat() : BigDecimal.ZERO);
            totalFiber = totalFiber.add(mf.getCalculatedFiber() != null ? mf.getCalculatedFiber() : BigDecimal.ZERO);
            totalCalcium = totalCalcium.add(mf.getCalculatedCalcium() != null ? mf.getCalculatedCalcium() : BigDecimal.ZERO);
            totalIron = totalIron.add(mf.getCalculatedIron() != null ? mf.getCalculatedIron() : BigDecimal.ZERO);
            totalSodium = totalSodium.add(mf.getCalculatedSodium() != null ? mf.getCalculatedSodium() : BigDecimal.ZERO);
            totalVitC = totalVitC.add(mf.getCalculatedVitaminC() != null ? mf.getCalculatedVitaminC() : BigDecimal.ZERO);
        }

        meal.setTotalCalories(totalCals);
        meal.setTotalProtein(totalProt);
        meal.setTotalCarbohydrate(totalCarbs);
        meal.setTotalFat(totalFat);
        meal.setTotalFiber(totalFiber);
        meal.setTotalCalcium(totalCalcium);
        meal.setTotalIron(totalIron);
        meal.setTotalSodium(totalSodium);
        meal.setTotalVitaminC(totalVitC);
    }

    /**
     * Busca todas as refeições de um usuário
     */
    public List<Meal> getUserMeals(UUID userId) {
        return mealRepository.findByUserIdOrderByDateDescCreatedAtDesc(userId);
    }

    /**
     * Busca refeições de um dia específico
     */
    public List<Meal> getMealsByDate(UUID userId, LocalDate date) {
        return mealRepository.findByUserIdAndDateOrderByCreatedAt(userId, date);
    }

    /**
     * Busca refeições entre datas
     */
    public List<Meal> getMealsBetweenDates(UUID userId, LocalDate startDate, LocalDate endDate) {
        return mealRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    /**
     * Deleta uma refeição
     */
    @Transactional
    public void deleteMeal(Long mealId, UUID userId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada"));

        // Verifica se a refeição pertence ao usuário
        if (!meal.getUser().getId().equals(userId)) {
            throw new RuntimeException("Você não tem permissão para deletar esta refeição");
        }

        mealRepository.delete(meal);
    }

    /**
     * Atualiza informações básicas da refeição
     */
    @Transactional
    public Meal updateMeal(Long mealId, UUID userId, MealDTO dto) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada"));

        if (!meal.getUser().getId().equals(userId)) {
            throw new RuntimeException("Você não tem permissão para atualizar esta refeição");
        }

        if (dto.getName() != null) meal.setName(dto.getName());
        if (dto.getType() != null) meal.setType(dto.getType());
        if (dto.getDate() != null) meal.setDate(dto.getDate());
        if (dto.getNotes() != null) meal.setNotes(dto.getNotes());

        return mealRepository.save(meal);
    }
}