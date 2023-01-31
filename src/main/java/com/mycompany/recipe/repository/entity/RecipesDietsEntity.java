package com.mycompany.recipe.repository.entity;

import com.mycompany.recipe.enums.Diet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recipes_diets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipesDietsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;
    @Enumerated(EnumType.STRING)
    private Diet name;
}
