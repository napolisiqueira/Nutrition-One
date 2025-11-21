package br.com.nutritionone.Nutrition_One.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data_user")
public class DataUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


}
