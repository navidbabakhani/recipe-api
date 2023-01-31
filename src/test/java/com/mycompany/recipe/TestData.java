package com.mycompany.recipe;

import com.mycompany.recipe.enums.Diet;
import com.mycompany.recipe.repository.entity.IngredientEntity;
import com.mycompany.recipe.repository.entity.RecipeEntity;
import com.mycompany.recipe.repository.entity.RecipesDietsEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TestData {

    public static UUID USER_ID = UUID.fromString("9f9349a6-19ce-46fe-a0cf-b4bffaba8772");

    public static RecipeEntity dummyRecipe() {
        RecipeEntity entity = new RecipeEntity();
        entity.setTitle("Pizza");
        entity.setInstruction("1. do this 2. do that 3. enjoy!");
        entity.setIngredients(List.of(new IngredientEntity(null, entity, "Bread", "4 pieces"), new IngredientEntity(null, entity, "Cheese", "1K")));
        entity.setDiets(Set.of(new RecipesDietsEntity(null, entity, Diet.VEGETARIAN), new RecipesDietsEntity(null, entity, Diet.GLUTEN_FREE)));
        entity.setTimeToPrepare(60);
        entity.setNumberOfServings(4);
        return entity;
    }

    public static RecipeEntity dummyRecipe2() {
        RecipeEntity entity = new RecipeEntity();
        entity.setTitle("Pasta");
        entity.setInstruction("1. do this 2. do that 3. enjoy!");
        entity.setIngredients(List.of(new IngredientEntity(null, entity, "Pasta", "1 pack"), new IngredientEntity(null, entity, "additions", "enough")));
        entity.setDiets(Set.of(new RecipesDietsEntity(null, entity, Diet.VEGAN)));
        entity.setTimeToPrepare(45);
        entity.setNumberOfServings(2);
        return entity;
    }
    public static List<RecipeEntity> recipeList = List.of(dummyRecipe(), dummyRecipe2());
}
