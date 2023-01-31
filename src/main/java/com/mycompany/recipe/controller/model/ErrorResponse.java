package com.mycompany.recipe.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String service;
    private Integer errorCode;
    private String errorMessage;
    private LocalDateTime dateTime;
}
