package com.ecommerce.app.user.application.service.ports.input.service;

import com.ecommerce.app.user.application.service.dto.create.*;
import com.ecommerce.app.user.domain.core.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserApplicationService {
    CreateUserResponse createUser(@Valid CreateUserCommand command);
    CreateVerifyEmailResponse createVerifyEmail(@Valid CreateVerifyEmailCommand command);
    CreateWarehouseAdminResponse createWarehouseAdmin(@Valid CreateWarehouseAdminCommand command);
    CreatePasswordResponse createPassword(@Valid UserIdQuery userIdQuery, @Valid CreatePasswordCommand command);
    List<User> getAllUsers();
    User getUserById(@Valid UserIdQuery userIdQuery);
    LoginUserResponse loginUser(@Valid LoginUserCommand loginUserCommand);
}
