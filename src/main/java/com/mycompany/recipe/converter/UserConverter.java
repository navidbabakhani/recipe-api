package com.mycompany.recipe.converter;

import com.mycompany.recipe.controller.model.User;
import com.mycompany.recipe.repository.entity.UserEntity;


public class UserConverter {

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmailAddress(user.getEmailAddress());
        return entity;
    }

    public static User toApiModel(UserEntity entity) {
        return new User(
                entity.getUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmailAddress()
        );
    }
}
