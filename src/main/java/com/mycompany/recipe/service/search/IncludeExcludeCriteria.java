package com.mycompany.recipe.service.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class IncludeExcludeCriteria<E> {

    private Set<E> include;
    private Set<E> exclude;

    public boolean hasAnyCriteria() {
        return (include != null && !include.isEmpty()) || (exclude != null && !exclude.isEmpty());
    }
}
