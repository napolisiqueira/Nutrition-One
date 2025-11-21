package br.com.nutritionone.Nutrition_One.controller;

import br.com.nutritionone.Nutrition_One.model.Food;
import br.com.nutritionone.Nutrition_One.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    /**
     * Buscar todos os alimentos
     * GET /foods
     */
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        return ResponseEntity.ok(foodRepository.findAll());
    }

    /**
     * Buscar alimento por ID
     * GET /foods/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable Long id) {
        return foodRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Buscar alimentos por nome (pesquisa parcial)
     * GET /foods/search?name=arroz
     */
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFoods(@RequestParam String name) {
        return ResponseEntity.ok(foodRepository.searchByName(name));
    }

    /**
     * Buscar por categoria
     * GET /foods/category/Cereais
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Food>> getFoodsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(foodRepository.findByCategory(category));
    }

    /**
     * Buscar alimentos verificados
     * GET /foods/verified
     */
    @GetMapping("/verified")
    public ResponseEntity<List<Food>> getVerifiedFoods() {
        return ResponseEntity.ok(foodRepository.findByVerifiedTrue());
    }

    /**
     * Criar novo alimento (admin/usuário pode adicionar)
     * POST /foods
     */
    @PostMapping
    public ResponseEntity<?> createFood(@RequestBody Food food) {
        try {
            food.setVerified(false); // Novo alimento não verificado
            Food saved = foodRepository.save(food);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Atualizar alimento
     * PUT /foods/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(@PathVariable Long id, @RequestBody Food food) {
        try {
            return foodRepository.findById(id)
                    .map(existing -> {
                        food.setId(id);
                        return ResponseEntity.ok(foodRepository.save(food));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deletar alimento
     * DELETE /foods/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {
        try {
            foodRepository.deleteById(id);
            return ResponseEntity.ok("Alimento deletado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}