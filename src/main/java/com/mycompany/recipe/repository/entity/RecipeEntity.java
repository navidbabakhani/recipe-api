package com.mycompany.recipe.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "recipes")
@Getter
@Setter
public class RecipeEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID userId;
    private String title;

    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<IngredientEntity> ingredients;
    private Integer numberOfServings;

    @Lob
    @Column(columnDefinition = "text")
    private String instruction;

    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private Set<RecipesDietsEntity> diets;

    private Integer timeToPrepare;

    @PrePersist
    private void prePersist() {
        ingredients.forEach(i -> i.setRecipe(this));
        diets.forEach(d -> d.setRecipe(this));
    }
}
