package com.ecommerce.app.user.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.application.service.config.UserServiceConfigData;
import com.ecommerce.app.user.application.service.dto.create.*;
import com.ecommerce.app.user.application.service.mapper.UserDataMapper;
import com.ecommerce.app.user.application.service.ports.output.message.publisher.user.UserCreatedMessagePublisher;
import com.ecommerce.app.user.application.service.ports.output.repository.UserRepository;
import com.ecommerce.app.user.domain.core.UserDomainService;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.event.UserCreatedEvent;
import com.ecommerce.app.user.domain.core.exception.UserException;
import com.ecommerce.app.user.domain.core.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserCreateHelper {
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;
    private final UserCreatedMessagePublisher userCreatedMessagePublisher;
    private final UserServiceConfigData userServiceConfigData;

    public UserCreateHelper(UserDomainService userDomainService,
                            UserRepository userRepository,
                            UserDataMapper userDataMapper,
                            UserCreatedMessagePublisher userCreatedMessagePublisher, UserServiceConfigData userServiceConfigData) {
        this.userDomainService = userDomainService;
        this.userRepository = userRepository;
        this.userDataMapper = userDataMapper;
        this.userCreatedMessagePublisher = userCreatedMessagePublisher;
        this.userServiceConfigData = userServiceConfigData;
    }

    /**
     * Persist user
     * @param createUserCommand CreateUserCommand
     * @return UserCreatedEvent
     */
    @Transactional
    public UserCreatedEvent persistUser(CreateUserCommand createUserCommand) {
        log.info("Creating user with username: {}", createUserCommand.getUsername());
        User user = userDataMapper.userCommandToUser(createUserCommand);
        UserCreatedEvent userCreatedEvent = userDomainService.userCreated(user, userCreatedMessagePublisher);
        saveUser(user);
        log.info("User created with username: {}", createUserCommand.getUsername());
        String token = generateTokenVerifyEmail(userCreatedEvent.getUser());
        userCreatedEvent.getUser().setToken(token);
        return userCreatedEvent;
    }

    /**
     * Persist email verification
     * @param command CreateVerifyEmailCommand
     * @return User
     */
    @Transactional
    public User persistEmailVerification(CreateVerifyEmailCommand command) {
        DecodedJWT decodedJWT = verifyToken(command.getToken());
        String email = decodedJWT.getClaim("email").asString();

        log.info("Verifying email for user with email: {}", email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            log.error("User with email: {} not found!", email);
            throw new UserNotFoundException("User with email " + email + " not found!");
        }
        log.info("username: {}", user.get().getUsername());

        userDomainService.verifyEmail(user.get());
        userRepository.save(user.get());
        return user.get();
    }

    /**
     * Persist password
     * @param userIdQuery UserIdQuery
     * @param command CreatePasswordCommand
     * @return User
     */
    @Transactional
    public User persistPassword(UserIdQuery userIdQuery, CreatePasswordCommand command) {
        log.info("Creating password for user with id: {}", userIdQuery.getUserId());

        Optional<User> user = userRepository.findById(new UserId(userIdQuery.getUserId()));
        if (user.isEmpty()) {
            log.error("User with id: {} not found!", userIdQuery.getUserId());
            throw new UserNotFoundException("User with id " + userIdQuery.getUserId() + " not found!");
        }

        userDomainService.createPassword(user.get(), command.getPassword(), command.getConfirmPassword());
        String passwordHash = BCrypt.hashpw(command.getPassword(), BCrypt.gensalt());
        user.get().setPassword(passwordHash);
        userRepository.save(user.get());
        return user.get();
    }

    /**
     * Persist login
     * @param user LoginUserCommand
     * @return LoginResponse
     */
    @Transactional
    public LoginResponse login(LoginUserCommand user) {
        User userResult = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with email " + user.getEmail() + " not found!"));
        userDomainService.login(userResult);
        if (!BCrypt.checkpw(user.getPassword(), userResult.getPassword())) {
            log.error("Invalid password for user with email: {}", user.getEmail());
            throw new UserException("Invalid password!");
        }
        String token = generateToken(userResult);
        log.info("User with email: {} logged in successfully!", user.getEmail());
        return new LoginResponse(userResult, token);
    }

    /**
     * get all users
     * @return User
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * get user by id
     * @param userIdQuery UserIdQuery
     * @return User
     */
    @Transactional(readOnly = true)
    public User getUserById(UserIdQuery userIdQuery) {
        return userRepository.findById(new UserId(userIdQuery.getUserId()))
                .orElseThrow(() -> new UserNotFoundException("User with id " + userIdQuery.getUserId() + " not found!"));
    }

    /**
     * get user by email
     * @param user User
     * @return User
     */
    private User saveUser(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(existingUser -> {
            log.error("User with username: {} already exists!", user.getUsername());
            throw new UserException("User with username " + user.getUsername() + " already exists!");
        });

        userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            log.error("User with email: {} already exists!", user.getEmail());
            throw new UserException("User with email " + user.getEmail() + " already exists!");
        });

        User userResult = userRepository.save(user);
        if (userResult == null) {
            log.error("User could not be saved!");
        }

        return userResult;
    }

    /**
     * generate token
     * @param user User
     * @return String
     */
    private String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getId().getValue().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withClaim("is_email_verified", user.isEmailVerified())
                .withClaim("warehouse_id", (user.getWarehouseId() != null
                        && user.getWarehouseId().getValue() != null) ? user.getWarehouseId().getValue().toString() : null)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 31_536_000_000L))
                .sign(Algorithm.HMAC256(userServiceConfigData.getSecretKey()));
    }

    /**
     * generate token for email verification
     * @param user User
     * @return String
     */
    private String generateTokenVerifyEmail(User user) {
        return JWT.create()
                .withSubject(user.getId().getValue().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withClaim("is_email_verified", user.isEmailVerified())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 7_200_000))
                .sign(Algorithm.HMAC256(userServiceConfigData.getSecretKey()));
    }

    /**
     * verify token
     * @param token String
     * @return DecodedJWT
     */
    private DecodedJWT verifyToken(String token) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.require(Algorithm.HMAC256(userServiceConfigData.getSecretKey()))
                .build()
                .verify(token);
        } catch (Exception e) {
            throw new UserException("Invalid token!");
        }
        if (decodedJWT.getExpiresAt().before(new Date())) {
            throw new UserException("Token has expired!");
        }
        return decodedJWT;
    }
}
