package com.mycompany.recipe.converter;

import com.mycompany.recipe.controller.model.Ingredient;
import com.mycompany.recipe.repository.entity.IngredientEntity;

public class IngredientConverter {

    public static IngredientEntity toEntity(Ingredient user) {
        IngredientEntity entity = new IngredientEntity();
        entity.setName(user.getName());
        entity.setAmount(user.getAmount());
        return entity;
    }

    public static Ingredient toApiModel(IngredientEntity entity) {
        return new Ingredient(
                entity.getName(),
                entity.getAmount()
        );
    }
}
