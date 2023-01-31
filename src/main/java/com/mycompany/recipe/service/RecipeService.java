package com.mycompany.recipe.service;

import com.mycompany.recipe.service.search.SearchCriteria;
import com.mycompany.recipe.exception.RecipeNotFoundException;
import com.mycompany.recipe.repository.RecipeRepository;
import com.mycompany.recipe.repository.entity.RecipeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class RecipeService {

    private RecipeRepository recipeRepository;
    private RecipeSearchService searchService;
    private UserService userService;

    public List<RecipeEntity> searchRecipes(UUID userId, Optional<SearchCriteria> searchCriteria) {
        userService.userExistsOrThrow(userId);

        return searchCriteria.isPresent() && searchCriteria.get().hasAnyCriteria() ?
                searchService.findAll(searchCriteria.get()) :
                recipeRepository.findAll();
    }

    public RecipeEntity createRecipe(UUID userId, RecipeEntity recipeEntity) {
        userService.userExistsOrThrow(userId);

        recipeEntity.setUserId(userId);
        return recipeRepository.save(recipeEntity);
    }

    public RecipeEntity updateRecipe(UUID userId, RecipeEntity recipeEntity) {
        userService.userExistsOrThrow(userId);

        entityExistsOrThrow(recipeEntity.getId());

        recipeEntity.setUserId(userId);
        return recipeRepository.save(recipeEntity);
    }

    public void deleteRecipe(UUID userId, Long recipeId) {
        userService.userExistsOrThrow(userId);

        entityExistsOrThrow(recipeId);

        recipeRepository.deleteById(recipeId);
    }

    private void entityExistsOrThrow(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) throw new RecipeNotFoundException(recipeId);
    }
}
