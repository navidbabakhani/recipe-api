package com.mycompany.recipe.service;

import com.mycompany.recipe.service.search.IncludeExcludeCriteria;
import com.mycompany.recipe.service.search.NumberOfServingsCriteria;
import com.mycompany.recipe.service.search.SearchCriteria;
import com.mycompany.recipe.repository.RecipeRepository;
import com.mycompany.recipe.repository.entity.IngredientEntity;
import com.mycompany.recipe.repository.entity.RecipeEntity;
import com.mycompany.recipe.repository.entity.RecipesDietsEntity;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class RecipeSearchService {

    private RecipeRepository recipeRepository;

    public List<RecipeEntity> findAll(SearchCriteria searchCriteria) {
        return recipeRepository.findAll(createSpecifications(searchCriteria));
    }

    private Specification<RecipeEntity> createSpecifications(SearchCriteria search) {
        return Specification.where(titleContains(search.getTitle()))
                .and(forHowMany(search.getNumberOfServings()))
                .and(includeIngredients(search.getIngredients()))
                .and(excludeIngredients(search.getIngredients()))
                .and(instructionContains(search.getInstruction()))
                .and(includeDiets(search.getDiets()))
                .and(excludeDiets(search.getDiets()));
    }

    private Specification<RecipeEntity> titleContains(String title) {
        if (Strings.isBlank(title)) return null;

        return (recipe, cq, cb) -> cb.like(cb.lower(recipe.get("title")), String.format("%%%s%%", title.toLowerCase()));
    }

    private Specification<RecipeEntity> forHowMany(NumberOfServingsCriteria numberOfServings) {
        if (numberOfServings == null) return null;

        switch (numberOfServings.getOperator()) {
            case ">":
                return (recipe, cq, cb) -> cb.gt(recipe.get("numberOfServings"), numberOfServings.getNumber());
            case ">=":
                return (recipe, cq, cb) -> cb.ge(recipe.get("numberOfServings"), numberOfServings.getNumber());
            case "<":
                return (recipe, cq, cb) -> cb.lt(recipe.get("numberOfServings"), numberOfServings.getNumber());
            case "<=":
                return (recipe, cq, cb) -> cb.le(recipe.get("numberOfServings"), numberOfServings.getNumber());
            case "=":
                return (recipe, cq, cb) -> cb.equal(recipe.get("numberOfServings"), numberOfServings.getNumber());
            case "!=":
                return (recipe, cq, cb) -> cb.notEqual(recipe.get("numberOfServings"), numberOfServings.getNumber());
            default:
                return null;
        }
    }

    private <E> Specification<RecipeEntity> includeIngredients(IncludeExcludeCriteria<E> ingredients) {
        if (ingredients == null || ingredients.getInclude() == null || ingredients.getInclude().isEmpty()) return null;

        return (recipe, cq, cb) -> recipe.join("ingredients").get("name").in(ingredients.getInclude());
    }

    private <E> Specification<RecipeEntity> excludeIngredients(IncludeExcludeCriteria<E> ingredients) {
        if (ingredients == null || ingredients.getExclude() == null || ingredients.getExclude().isEmpty()) return null;

        return (recipe, query, cb) -> {
            Subquery<RecipeEntity> subQuery = query.subquery(RecipeEntity.class);
            Root<IngredientEntity> ingredientRoot = subQuery.from(IngredientEntity.class);
            Join<IngredientEntity, RecipeEntity> join = ingredientRoot.join("recipe");
            subQuery.select(join).where(ingredientRoot.get("name").in(ingredients.getExclude()));

            return cb.in(recipe).value(subQuery).not();
        };
    }

    private Specification<RecipeEntity> instructionContains(String toSearch) {
        if (Strings.isBlank(toSearch)) return null;

        return (recipe, cq, cb) -> cb.like(cb.lower(recipe.get("instruction")), String.format("%%%s%%", toSearch.toLowerCase()));
    }

    private <E> Specification<RecipeEntity> includeDiets(IncludeExcludeCriteria<E> diets) {
        if (diets == null || diets.getInclude() == null || diets.getInclude().isEmpty()) return null;

        return (recipe, cq, cb) -> recipe.join("diets").get("name").in(diets.getInclude());
    }

    private <E> Specification<RecipeEntity> excludeDiets(IncludeExcludeCriteria<E> diets) {
        if (diets == null || diets.getExclude() == null || diets.getExclude().isEmpty()) return null;

        return (recipe, query, cb) -> {
            Subquery<RecipeEntity> subQuery = query.subquery(RecipeEntity.class);
            Root<RecipesDietsEntity> dietRoot = subQuery.from(RecipesDietsEntity.class);
            Join<RecipesDietsEntity, RecipeEntity> join = dietRoot.join("recipe");
            subQuery.select(join).where(dietRoot.get("name").in(diets.getExclude()));

            return cb.in(recipe).value(subQuery).not();
        };
    }
}
