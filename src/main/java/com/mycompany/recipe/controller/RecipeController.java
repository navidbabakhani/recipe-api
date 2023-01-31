package com.mycompany.recipe.controller;

import com.mycompany.recipe.service.search.SearchCriteria;
import com.mycompany.recipe.converter.RecipeConverter;
import com.mycompany.recipe.controller.model.RecipeRequestBody;
import com.mycompany.recipe.controller.model.RecipeResponseBody;
import com.mycompany.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeResponseBody>> searchRecipes(
            @NotNull  @RequestHeader UUID userId,
            @Valid @RequestBody(required = false) Optional<SearchCriteria> searchCriteria
    ) {
        return ResponseEntity.ok(recipeService.searchRecipes(userId, searchCriteria)
                .stream()
                .map(RecipeConverter::toApiModel)
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RecipeResponseBody> createRecipe(
            @NotNull @RequestHeader UUID userId,
            @Valid @RequestBody RecipeRequestBody recipeRequestBody
    ) {
        return ResponseEntity.ok(RecipeConverter.toApiModel(recipeService.createRecipe(userId, RecipeConverter.toEntity(recipeRequestBody))));
    }

    @PutMapping
    public ResponseEntity<RecipeResponseBody> updateRecipe(
            @NotNull @RequestHeader UUID userId,
            @Valid @RequestBody RecipeRequestBody recipeRequestBody
    ) {
        return ResponseEntity.ok(RecipeConverter.toApiModel(recipeService.updateRecipe(userId, RecipeConverter.toEntity(recipeRequestBody))));
    }

    @DeleteMapping("/{recipeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteRecipe(
            @NotNull @RequestHeader UUID userId,
            @PathVariable Long recipeId
    ) {
        recipeService.deleteRecipe(userId, recipeId);
        return ResponseEntity.noContent().build();
    }
}
