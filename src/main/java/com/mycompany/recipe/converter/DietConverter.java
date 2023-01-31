package com.mycompany.recipe.converter;

import com.mycompany.recipe.enums.Diet;
import com.mycompany.recipe.repository.entity.RecipesDietsEntity;

public class DietConverter {

    public static RecipesDietsEntity toEntity(Diet diet) {
        RecipesDietsEntity entity = new RecipesDietsEntity();
        entity.setName(diet);
        return entity;
    }

    public static Diet toApiModel(RecipesDietsEntity entity) {
        return entity.getName();
    }
}
