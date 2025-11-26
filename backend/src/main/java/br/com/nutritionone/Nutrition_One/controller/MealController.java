package br.com.nutritionone.Nutrition_One.controller;

import br.com.nutritionone.Nutrition_One.dto.Meals.MealDTO;
import br.com.nutritionone.Nutrition_One.dto.Meals.MealFoodDTO;
import br.com.nutritionone.Nutrition_One.model.Meal;
import br.com.nutritionone.Nutrition_One.model.User;
import br.com.nutritionone.Nutrition_One.repository.UserRepository;
import br.com.nutritionone.Nutrition_One.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Criar nova refeição
     * POST /meals
     */
    @PostMapping
    public ResponseEntity<?> createMeal(@RequestBody MealDTO dto, Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            Meal meal = mealService.createMeal(user.getId(), dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(meal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Buscar todas as refeições do usuário
     * GET /meals
     */
    @GetMapping
    public ResponseEntity<?> getUserMeals(Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            List<Meal> meals = mealService.getUserMeals(user.getId());
            return ResponseEntity.ok(meals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Buscar uma refeição específica
     * GET /meals/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getMeal(@PathVariable Long id, Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            // Busca e valida se pertence ao usuário
            List<Meal> meals = mealService.getUserMeals(user.getId());
            Meal meal = meals.stream()
                    .filter(m -> m.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Refeição não encontrada"));
            return ResponseEntity.ok(meal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Buscar refeições de um dia específico
     * GET /meals/date/2024-11-21
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<?> getMealsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            List<Meal> meals = mealService.getMealsByDate(user.getId(), date);
            return ResponseEntity.ok(meals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Buscar refeições de hoje
     * GET /meals/today
     */
    @GetMapping("/today")
    public ResponseEntity<?> getTodayMeals(Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            List<Meal> meals = mealService.getMealsByDate(user.getId(), LocalDate.now());
            return ResponseEntity.ok(meals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Buscar refeições entre datas
     * GET /meals/range?start=2024-11-01&end=2024-11-30
     */
    @GetMapping("/range")
    public ResponseEntity<?> getMealsByRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            List<Meal> meals = mealService.getMealsBetweenDates(user.getId(), start, end);
            return ResponseEntity.ok(meals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Adicionar alimento à refeição
     * POST /meals/{id}/foods
     */
    @PostMapping("/{id}/foods")
    public ResponseEntity<?> addFood(
            @PathVariable Long id,
            @RequestBody MealFoodDTO foodDto,
            Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            Meal meal = mealService.addFoodToMeal(id, foodDto);
            return ResponseEntity.ok(meal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Remover alimento da refeição
     * DELETE /meals/{mealId}/foods/{foodId}
     */
    @DeleteMapping("/{mealId}/foods/{foodId}")
    public ResponseEntity<?> removeFood(
            @PathVariable Long mealId,
            @PathVariable Long foodId,
            Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            Meal meal = mealService.removeFoodFromMeal(mealId, foodId);
            return ResponseEntity.ok(meal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Atualizar quantidade de um alimento
     * PATCH /meals/{mealId}/foods/{foodId}
     */
    @PatchMapping("/{mealId}/foods/{foodId}")
    public ResponseEntity<?> updateFoodQuantity(
            @PathVariable Long mealId,
            @PathVariable Long foodId,
            @RequestBody Map<String, BigDecimal> body,
            Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            BigDecimal newQuantity = body.get("quantity");
            if (newQuantity == null) {
                return ResponseEntity.badRequest().body("Quantidade não informada");
            }
            Meal meal = mealService.updateFoodQuantity(mealId, foodId, newQuantity);
            return ResponseEntity.ok(meal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Atualizar informações da refeição
     * PUT /meals/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMeal(
            @PathVariable Long id,
            @RequestBody MealDTO dto,
            Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            Meal meal = mealService.updateMeal(id, user.getId(), dto);
            return ResponseEntity.ok(meal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deletar refeição
     * DELETE /meals/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeal(@PathVariable Long id, Authentication auth) {
        try {
            User user = getUserFromAuth(auth);
            mealService.deleteMeal(id, user.getId());
            return ResponseEntity.ok("Refeição deletada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Helper
    private User getUserFromAuth(Authentication auth) {
        return userRepository.findByUsername(auth.getName());
    }
}
