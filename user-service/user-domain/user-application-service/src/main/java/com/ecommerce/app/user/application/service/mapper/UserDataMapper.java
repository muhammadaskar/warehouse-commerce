package com.ecommerce.app.user.application.service.mapper;

import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminCommand;
import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminResponse;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {
    public User warehouseAdminCommandToUser(CreateWarehouseAdminCommand createWarehouseAdminCommand) {
        return User.builder()
                .withUsername(createWarehouseAdminCommand.getUsername())
                .withEmail(createWarehouseAdminCommand.getEmail())
                .build();
    }

    public CreateWarehouseAdminResponse warehouseAdminToCreateWarehouseAdminResponse(User user, String message) {
        return CreateWarehouseAdminResponse.builder()
                .userId(user.getId().getValue())
                .username(user.getUsername())
                .email(user.getEmail())
                .message(message)
                .build();
    }
}
