package com.mycompany.recipe.converter;

import com.mycompany.recipe.controller.model.RecipeRequestBody;
import com.mycompany.recipe.controller.model.RecipeResponseBody;
import com.mycompany.recipe.repository.entity.RecipeEntity;

import java.util.stream.Collectors;

public class RecipeConverter {

    public static RecipeEntity toEntity(RecipeRequestBody requestBody) {
        RecipeEntity entity = new RecipeEntity();
        entity.setId(requestBody.getId());
        entity.setTitle(requestBody.getTitle());
        entity.setInstruction(requestBody.getInstruction());
        entity.setIngredients(requestBody.getIngredients().stream().map(IngredientConverter::toEntity).collect(Collectors.toList()));
        entity.setDiets(requestBody.getDiets().stream().map(DietConverter::toEntity).collect(Collectors.toSet()));
        entity.setTimeToPrepare(requestBody.getTimeToPrepare());
        entity.setNumberOfServings(requestBody.getNumberOfServings());
        return entity;
    }

    public static RecipeResponseBody toApiModel(RecipeEntity entity) {
        return new RecipeResponseBody(
                entity.getId(),
                entity.getTitle(),
                entity.getIngredients().stream().map(IngredientConverter::toApiModel).collect(Collectors.toList()),
                entity.getNumberOfServings(),
                entity.getInstruction(),
                entity.getDiets().stream().map(DietConverter::toApiModel).collect(Collectors.toSet()),
                entity.getTimeToPrepare(),
                entity.getCreatedAt()
        );
    }
}
