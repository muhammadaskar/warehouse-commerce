package com.ecommerce.app.user.dataaccess.user.mapper;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.dataaccess.user.entity.UserEntity;
import com.ecommerce.app.user.domain.core.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {
    public UserEntity userToUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId().getValue())
                .username(user.getUsername())
                .email(user.getEmail())
                .isEmailVerified(user.isEmailVerified())
                .role(user.getRole())
                .build();
    }

    public User userEntityToUser(UserEntity userEntity) {
        return User.builder()
                .withId(new UserId(userEntity.getId()))
                .withUsername(userEntity.getUsername())
                .withEmail(userEntity.getEmail())
                .withPassword(userEntity.getPassword())
                .withRole(userEntity.getRole())
                .build();
    }
}
