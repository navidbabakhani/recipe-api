package com.mycompany.recipe.controller;

import com.mycompany.recipe.controller.model.Ingredient;
import com.mycompany.recipe.controller.model.RecipeRequestBody;
import com.mycompany.recipe.enums.Diet;
import com.mycompany.recipe.repository.RecipeRepository;
import com.mycompany.recipe.repository.entity.RecipeEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static com.mycompany.recipe.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("tst")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;
    private static HttpHeaders headers;

    @Autowired
    RecipeRepository recipeRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();

        headers = new HttpHeaders();
        headers.set("userId", USER_ID.toString());
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl + ":" + port + "/api/v1/recipes";
        recipeRepository.saveAll(recipeList);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void searchRecipes() {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<RecipeEntity>> response = restTemplate.exchange(baseUrl, GET, requestEntity, new ParameterizedTypeReference<>() {
        });
        List<RecipeEntity> list = response.getBody();

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @Order(2)
    void createRecipe() {
        RecipeRequestBody recipeRequestBody = new RecipeRequestBody();
        recipeRequestBody.setTitle("Spaghetti");
        recipeRequestBody.setInstruction("1. do this 2. do that 3. enjoy!");
        recipeRequestBody.setIngredients(List.of(new Ingredient("Spaghetti", "4 pieces"), new Ingredient("tomato", "1K")));
        recipeRequestBody.setDiets(Set.of(Diet.VEGETARIAN, Diet.GLUTEN_FREE));
        recipeRequestBody.setTimeToPrepare(30);
        recipeRequestBody.setNumberOfServings(4);

        HttpEntity<RecipeRequestBody> requestEntity = new HttpEntity<>(recipeRequestBody, headers);

        RecipeEntity newRecipe = restTemplate.exchange(baseUrl, POST, requestEntity, RecipeEntity.class).getBody();

        assertNotNull(newRecipe);
        assertThat(newRecipe.getId()).isNotNull();
    }

    @Test
    @Order(3)
    void updateRecipe() {
        RecipeRequestBody recipeRequestBody = new RecipeRequestBody();
        recipeRequestBody.setId(1L);
        recipeRequestBody.setTitle("Spaghetti");
        recipeRequestBody.setInstruction("1. do this 2. do that 3. enjoy!");
        recipeRequestBody.setIngredients(List.of(new Ingredient("Spaghetti", "4 pieces"), new Ingredient("tomato", "1K")));
        recipeRequestBody.setDiets(Set.of(Diet.VEGETARIAN, Diet.GLUTEN_FREE));
        recipeRequestBody.setTimeToPrepare(30);
        recipeRequestBody.setNumberOfServings(4);

        HttpEntity<RecipeRequestBody> requestEntity = new HttpEntity<>(recipeRequestBody, headers);

        RecipeEntity newRecipe = restTemplate.exchange(baseUrl, PUT, requestEntity, RecipeEntity.class).getBody();

        assertNotNull(newRecipe);
        assertThat(newRecipe.getId()).isNotNull();
        assertThat(newRecipe.getTitle()).isEqualTo("Spaghetti");
    }

    @Test
    @Order(4)
    void deleteRecipe() {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);


        List<RecipeEntity> list = restTemplate.exchange(baseUrl, GET, requestEntity, new ParameterizedTypeReference<List<RecipeEntity>>() {
        }).getBody();
        assertThat(list.size()).isEqualTo(3);

        restTemplate.exchange(baseUrl + "/1", DELETE, requestEntity, Void.class);

        list = restTemplate.exchange(baseUrl, GET, requestEntity, new ParameterizedTypeReference<List<RecipeEntity>>() {
        }).getBody();
        assertThat(list.size()).isEqualTo(2);
    }
}