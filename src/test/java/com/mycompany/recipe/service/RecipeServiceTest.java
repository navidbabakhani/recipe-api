package com.mycompany.recipe.service;

import com.mycompany.recipe.service.search.SearchCriteria;
import com.mycompany.recipe.exception.UserNotFoundException;
import com.mycompany.recipe.repository.RecipeRepository;
import com.mycompany.recipe.repository.entity.RecipeEntity;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mycompany.recipe.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeSearchService searchService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserService userService;

    @Nested
    class SearchRecipes {
        @Test
        void returnAllWhenNoSearchCriteriaIsProvided() {
            doNothing().when(userService).userExistsOrThrow(USER_ID);
            when(recipeRepository.findAll()).thenReturn(recipeList);

            List<RecipeEntity> recipeEntities = recipeService.searchRecipes(USER_ID, Optional.empty());

            assertThat(recipeEntities)
                    .hasSize(2)
                    .has(new Condition<>(list -> list.get(0).getTitle().equals("Pizza"), ""))
                    .has(new Condition<>(list -> list.get(1).getTitle().equals("Pasta"), ""));
        }

        @Test
        void returnFilteredRecipesWhenSearchCriteriaIsProvided() {
            SearchCriteria searchCriteria = new SearchCriteria("Pizza", null, null, null, null);

            doNothing().when(userService).userExistsOrThrow(USER_ID);
            when(searchService.findAll(searchCriteria)).thenReturn(recipeList);

            List<RecipeEntity> recipeEntities = recipeService.searchRecipes(USER_ID, Optional.of(searchCriteria));

            assertThat(recipeEntities)
                    .hasSize(2)
                    .has(new Condition<>(list -> list.get(0).getTitle().equals("Pizza"), ""))
                    .has(new Condition<>(list -> list.get(1).getTitle().equals("Pasta"), ""));
        }

        @Test
        void returnAllRecipesWhenSearchCriteriaFieldsAreNull() {
            SearchCriteria searchCriteria = new SearchCriteria(null, null, null, null, null);

            doNothing().when(userService).userExistsOrThrow(USER_ID);
            when(recipeRepository.findAll()).thenReturn(recipeList);

            List<RecipeEntity> recipeEntities = recipeService.searchRecipes(USER_ID, Optional.of(searchCriteria));

            assertThat(recipeEntities)
                    .hasSize(2)
                    .has(new Condition<>(list -> list.get(0).getTitle().equals("Pizza"), ""))
                    .has(new Condition<>(list -> list.get(1).getTitle().equals("Pasta"), ""));
        }

        @Test
        void shouldThrowExceptionWhenUserIsNotFound() {
            doThrow(UserNotFoundException.class).when(userService).userExistsOrThrow(any(UUID.class));

            assertThrows(UserNotFoundException.class, () -> recipeService.searchRecipes(UUID.randomUUID(), Optional.empty()));
        }
    }

    @Nested
    class CreateRecipe {

        @Captor
        ArgumentCaptor<RecipeEntity> recipeCaptor;

        @Test
        void canCreateRecipe() {
            doNothing().when(userService).userExistsOrThrow(USER_ID);

            when(recipeRepository.save(recipeCaptor.capture())).thenReturn(dummyRecipe());

            recipeService.createRecipe(USER_ID, dummyRecipe());

            assertEquals("Pizza", recipeCaptor.getValue().getTitle());
            assertEquals(USER_ID, recipeCaptor.getValue().getUserId());
        }

        @Test
        void shouldThrowExceptionWhenUserIsNotFound() {
            doThrow(UserNotFoundException.class).when(userService).userExistsOrThrow(any(UUID.class));

            assertThrows(UserNotFoundException.class, () -> recipeService.createRecipe(UUID.randomUUID(), dummyRecipe()));
        }
    }

    @Test
    void updateRecipe() {
    }

    @Test
    void deleteRecipe() {
    }
}