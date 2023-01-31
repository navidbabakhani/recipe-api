package com.mycompany.recipe.controller.model;

import com.mycompany.recipe.enums.Diet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequestBody {

    private Long id;

    @NotBlank
    private String title;
    private String description;

    @Valid
    @NotNull
    @NotEmpty
    private List<Ingredient> ingredients = null;

    @NotNull
    private Integer numberOfServings;
    @NotBlank
    private String instruction;

    @Valid
    @NotNull
    @NotEmpty
    private Set<Diet> diets = null;

    private Integer timeToPrepare;
}
