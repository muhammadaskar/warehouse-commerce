package com.ecommerce.app.payment.dataaccess.user.mapper;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.payment.dataaccess.user.entity.UserEntity;
import com.ecommerce.app.payment.domain.core.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {

    public User userEntityToUser(UserEntity userEntity) {
        return User.newBuilder()
                .withId(new UserId(userEntity.getId()))
                .withWarehouseId(new WarehouseId(userEntity.getWarehouseId()))
                .withEmail(userEntity.getEmail())
                .withIsEmailVerified(userEntity.isEmailVerified())
                .withUserRole(userEntity.getRole())
                .build();
    }

    public UserEntity userToUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId().getValue())
                .warehouseId(user.getWarehouseId().getValue())
                .email(user.getEmail())
                .isEmailVerified(user.isEmailVerified())
                .role(user.getUserRole())
                .build();
    }
}
