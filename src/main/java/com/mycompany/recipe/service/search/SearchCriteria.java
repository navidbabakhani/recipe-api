package com.mycompany.recipe.service.search;

import com.mycompany.recipe.enums.Diet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import javax.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

    private String title;
    @Valid
    private NumberOfServingsCriteria numberOfServings;
    private IncludeExcludeCriteria<String> ingredients;
    private String instruction;
    private IncludeExcludeCriteria<Diet> diets;

    public boolean hasAnyCriteria() {
        return Strings.isNotBlank(title)
                || numberOfServings != null
                || (ingredients != null && ingredients.hasAnyCriteria())
                || Strings.isNotBlank(instruction)
                || (diets != null && diets.hasAnyCriteria());
    }
}
