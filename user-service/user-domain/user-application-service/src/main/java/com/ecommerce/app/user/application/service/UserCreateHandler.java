package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.dto.create.*;
import com.ecommerce.app.user.application.service.mapper.UserDataMapper;
import com.ecommerce.app.user.application.service.ports.output.message.publisher.user.UserCreatedMessagePublisher;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserCreateHandler {

    private final UserCreateHelper userCreateHelper;
    private final UserDataMapper userDataMapper;
    private final UserCreatedMessagePublisher userCreatedMessagePublisher;

    public UserCreateHandler(UserCreateHelper userCreateHelper,
                             UserDataMapper userDataMapper,
                             UserCreatedMessagePublisher userCreatedMessagePublisher) {
        this.userCreateHelper = userCreateHelper;
        this.userDataMapper = userDataMapper;
        this.userCreatedMessagePublisher = userCreatedMessagePublisher;
    }

    public CreateUserResponse createUser(CreateUserCommand createUserCommand) {
        UserCreatedEvent userCreatedEvent = userCreateHelper.persistUser(createUserCommand);
        userCreatedMessagePublisher.publish(userCreatedEvent);
        return userDataMapper.userToCreateUserResponse(userCreatedEvent.getUser(), "User created successfully!");
    }

    public CreateVerifyEmailResponse verifyEmail(CreateVerifyEmailCommand createVerifyEmailCommand) {
        User user = userCreateHelper.persistEmailVerification(createVerifyEmailCommand);
        return userDataMapper.userToCreateVerifyEmailResponse(user, "Email verified successfully!");
    }

    public CreatePasswordResponse createPassword(UserIdQuery userIdQuery, CreatePasswordCommand createPasswordCommand) {
        userCreateHelper.persistPassword(userIdQuery, createPasswordCommand);
        return userDataMapper.passwordToCreatePasswordResponse("Password created successfully!");
    }

    public LoginUserResponse loginUser(LoginUserCommand loginUserCommand) {
        LoginResponse user = userCreateHelper.login(loginUserCommand);
        return userDataMapper.userToLoginUserResponse(user.getUser(), user.getToken(), "User logged in successfully!");
    }

    public List<User> getAllUsers() {
        return userCreateHelper.getAllUsers();
    }

    public User getUserById(UserIdQuery userIdQuery) {
        return userCreateHelper.getUserById(userIdQuery);
    }
}
