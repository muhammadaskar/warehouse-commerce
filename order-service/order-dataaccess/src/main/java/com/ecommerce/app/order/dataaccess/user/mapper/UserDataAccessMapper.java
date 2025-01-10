package com.ecommerce.app.order.dataaccess.user.mapper;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.order.dataaccess.user.entity.UserEntity;
import com.ecommerce.app.order.domain.core.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {

    public UserEntity userEntityToUser(User user) {
        return UserEntity.builder()
                .id(user.getId().getValue())
                .warehouseId(user.getWarehouseId().getValue())
                .email(user.getEmail())
                .isEmailVerified(user.isEmailVerified())
                .role(user.getRole())
                .build();
    }

    public User userEntityToUser(UserEntity userEntity) {
        return User.builder()
                .withId(new UserId(userEntity.getId()))
                .withWarehouseId(new WarehouseId(userEntity.getWarehouseId()))
                .withEmail(userEntity.getEmail())
                .withIsEmailVerified(userEntity.isEmailVerified())
                .withRole(userEntity.getRole())
                .build();
    }
}
