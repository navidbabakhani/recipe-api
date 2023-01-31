package com.mycompany.recipe.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super(String.format("User id '%s' does not exist.", userId));
    }
}
