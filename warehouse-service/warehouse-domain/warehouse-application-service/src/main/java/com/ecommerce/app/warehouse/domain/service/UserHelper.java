package com.ecommerce.app.warehouse.domain.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.UserDomainService;
import com.ecommerce.app.warehouse.domain.core.entity.User;
import com.ecommerce.app.warehouse.domain.core.exception.UserUnauthorized;
import com.ecommerce.app.warehouse.domain.service.config.WarehouseServiceConfigData;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class UserHelper {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final GlobalVerifyJWT globalVerifyJWT;
    private final WarehouseServiceConfigData warehouseServiceConfigData;

    public UserHelper(UserRepository userRepository, UserDomainService userDomainService, GlobalVerifyJWT globalVerifyJWT, WarehouseServiceConfigData warehouseServiceConfigData) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.globalVerifyJWT = globalVerifyJWT;
        this.warehouseServiceConfigData = warehouseServiceConfigData;
    }

    /**
     * Check if user is active through userDomainService
     * @param userId String
     */
    public User checkActiveUser(String userId) {
        User user = findByUser(new UserId(UUID.fromString(userId)));
        userDomainService.checkActiveUser(user);
        return user;
    }

    /**
     * Find user by userId, this is function for this class only
     * @param userId UserId
     * @return user User
     */
    private User findByUser(UserId userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserUnauthorized("User not found");
        }
        return user.get();
    }

    /**
     * Get user from logged in user, from AuthorizationHeader object using JWT
     * @param authorizationHeader AuthorizationHeader
     * @return User
     */
    public User getUserFromLoggedIn(AuthorizationHeader authorizationHeader) {
        String token = authorizationHeader.getAuthorization().replace("Bearer ", "");;
        DecodedJWT decodedJWT = globalVerifyJWT.verifyJWT(token, warehouseServiceConfigData.getSecretKey());
        if (decodedJWT == null) {
            throw new UserUnauthorized("User not authorized");
        }
        return User.newBuilder()
                .withId(new UserId(UUID.fromString(decodedJWT.getSubject())))
                .withUserRole(UserRole.valueOf(decodedJWT.getClaim("role").asString()))
                .withIsEmailVerified(decodedJWT.getClaim("is_email_verified").asBoolean())
                .withWarehouseId(decodedJWT.getClaim("warehouse_id").isNull() ? null : new WarehouseId(UUID.fromString(decodedJWT.getClaim("warehouse_id").asString())))
                .build();
    }
}
