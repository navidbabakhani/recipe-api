package com.mycompany.recipe.service.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class NumberOfServingsCriteria {
    @NotNull private Integer number;
    @NotNull private String operator;
}