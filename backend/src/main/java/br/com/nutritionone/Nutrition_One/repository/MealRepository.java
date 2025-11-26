package br.com.nutritionone.Nutrition_One.repository;

import br.com.nutritionone.Nutrition_One.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, Long> {

    // Buscar refeições de um usuário
    List<Meal> findByUserIdOrderByDateDescCreatedAtDesc(UUID userId);

    // Buscar refeições de um usuário em uma data específica
    List<Meal> findByUserIdAndDateOrderByCreatedAt(UUID userId, LocalDate date);

    // Buscar refeições por tipo
    List<Meal> findByUserIdAndType(UUID userId, Meal.MealType type);

    // Buscar refeições entre datas
    @Query("SELECT m FROM Meal m WHERE m.user.id = :userId AND m.date BETWEEN :startDate AND :endDate ORDER BY m.date DESC, m.createdAt ASC")
    List<Meal> findByUserIdAndDateBetween(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Contar refeições de um dia
    long countByUserIdAndDate(UUID userId, LocalDate date);
}