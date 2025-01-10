package com.ecommerce.app.order.application.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.order.application.service.config.OrderConfigData;
import com.ecommerce.app.order.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.order.application.service.ports.output.UserRepository;
import com.ecommerce.app.order.domain.core.UserDomainService;
import com.ecommerce.app.order.domain.core.entity.User;
import com.ecommerce.app.order.domain.core.exception.UserUnauthorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class UserHelper {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final OrderConfigData orderConfigData;
    private final GlobalVerifyJWT globalVerifyJWT;

    public UserHelper(UserRepository userRepository, UserDomainService userDomainService, OrderConfigData orderConfigData, GlobalVerifyJWT globalVerifyJWT) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.orderConfigData = orderConfigData;
        this.globalVerifyJWT = globalVerifyJWT;
    }

    /**
     * check if user is active
     * @param userId user id
     * @return user
     */
    @Transactional
    public User checkActiveUser(String userId) {
        log.info("Checking if user is active with id: {}", userId);
        Optional<User> user = userRepository.findById(new UserId(UUID.fromString(userId)));
        if (user.isEmpty()) {
            log.error("User not found with id: {}", userId);
            throw new RuntimeException("User not found with id: " + userId);
        }
        userDomainService.checkActiveUser(user.get());
        log.info("User is active with id: {}", userId);
        return user.get();
    }

    /**
     * get user from logged in user
     * @param authorizationHeader authorizationHeader
     * @return user
     */
    public User getUserFromLoggedIn(AuthorizationHeader authorizationHeader) {
        String token = authorizationHeader.getAuthorization().replace("Bearer ", "");;
        DecodedJWT decodedJWT = globalVerifyJWT.verifyJWT(token, orderConfigData.getSecretKey());
        if (decodedJWT == null) {
            throw new UserUnauthorized("User not authorized");
        }
        return User.builder()
                .withId(new UserId(UUID.fromString(decodedJWT.getSubject())))
                .withRole(UserRole.valueOf(decodedJWT.getClaim("role").asString()))
                .withEmail(decodedJWT.getClaim("email").asString())
                .withIsEmailVerified(decodedJWT.getClaim("is_email_verified").asBoolean())
                .withWarehouseId(decodedJWT.getClaim("warehouse_id").isNull() ? null : new WarehouseId(UUID.fromString(decodedJWT.getClaim("warehouse_id").asString())))
                .build();
    }
}
