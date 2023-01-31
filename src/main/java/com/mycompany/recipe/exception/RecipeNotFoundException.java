package com.mycompany.recipe.exception;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(Long id) {
        super(String.format("Recipe '%d' does not exist.", id));
    }
}
