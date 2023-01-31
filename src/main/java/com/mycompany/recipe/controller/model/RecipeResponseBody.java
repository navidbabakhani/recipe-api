package com.mycompany.recipe.controller.model;

import com.mycompany.recipe.enums.Diet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseBody {

  private Long id;

  private String title;

  @Valid
  private List<Ingredient> ingredients = null;

  private Integer numberOfServings;

  private String instruction;

  @Valid
  private Set<Diet> diets = null;

  private Integer timeToPrepare;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdAt;
}
