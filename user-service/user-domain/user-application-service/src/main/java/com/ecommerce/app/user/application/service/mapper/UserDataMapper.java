package com.ecommerce.app.user.application.service.mapper;

import com.ecommerce.app.user.application.service.dto.create.*;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {
    public User userCommandToUser(CreateUserCommand createUserCommand) {
        return User.builder()
                .withUsername(createUserCommand.getUsername())
                .withEmail(createUserCommand.getEmail())
                .build();
    }

    public CreateUserResponse userToCreateUserResponse(User user, String message) {
        return CreateUserResponse.builder()
                .userId(user.getId().getValue())
                .username(user.getUsername())
                .email(user.getEmail())
                .token(user.getToken())
                .message(message)
                .build();
    }

    public CreateVerifyEmailResponse userToCreateVerifyEmailResponse(User user, String message) {
        return CreateVerifyEmailResponse.builder()
                .email(user.getEmail())
                .message(message)
                .build();
    }

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

    public User passwordCommandToUser(CreatePasswordCommand createPasswordCommand) {
        return User.builder()
                .withPassword(createPasswordCommand.getPassword())
                .build();
    }

    public CreatePasswordResponse passwordToCreatePasswordResponse(String message) {
        return CreatePasswordResponse.builder()
                .message(message)
                .build();
    }

    public LoginUserResponse userToLoginUserResponse(User user, String token, String message) {
        return LoginUserResponse.builder()
                .token(token)
                .userId(user.getId().getValue())
                .email(user.getEmail())
                .role(user.getRole().name())
                .warehouseId(user.getWarehouseId().getValue())
                .message(message)
                .build();
    }
}
