package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.dto.create.*;
import com.ecommerce.app.user.application.service.ports.input.service.UserApplicationService;
import com.ecommerce.app.user.domain.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Validated
@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserCreateHandler userCreateHandler;
    private final WarehouseAdminCreateHandler warehouseAdminCreateHandler;

    public UserApplicationServiceImpl(UserCreateHandler userCreateHandler, WarehouseAdminCreateHandler warehouseAdminCreateHandler) {
        this.userCreateHandler = userCreateHandler;
        this.warehouseAdminCreateHandler = warehouseAdminCreateHandler;
    }

    @Override
    public CreateUserResponse createUser(CreateUserCommand command) {
        return userCreateHandler.createUser(command);
    }

    @Override
    public CreateVerifyEmailResponse createVerifyEmail(CreateVerifyEmailCommand command) {
        return userCreateHandler.verifyEmail(command);
    }

    @Override
    public CreateWarehouseAdminResponse createWarehouseAdmin(CreateWarehouseAdminCommand command) {
        return warehouseAdminCreateHandler.createWarehouseAdmin(command);
    }

    @Override
    public CreatePasswordResponse createPassword(UserIdQuery userIdQuery, CreatePasswordCommand command) {
        return userCreateHandler.createPassword(userIdQuery, command);
    }

    @Override
    public List<User> getAllUsers() {
        return userCreateHandler.getAllUsers();
    }

    @Override
    public User getUserById(UserIdQuery userIdQuery) {
        return userCreateHandler.getUserById(userIdQuery);
    }

    @Override
    public LoginUserResponse loginUser(LoginUserCommand loginUserCommand) {
        return userCreateHandler.loginUser(loginUserCommand);
    }
}
