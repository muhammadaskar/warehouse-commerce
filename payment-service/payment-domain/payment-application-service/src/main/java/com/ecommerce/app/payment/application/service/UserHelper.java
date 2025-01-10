package com.ecommerce.app.payment.application.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.payment.application.service.config.PaymentConfigData;
import com.ecommerce.app.payment.application.service.ports.output.UserRepository;
import com.ecommerce.app.payment.domain.core.UserDomainService;
import com.ecommerce.app.payment.domain.core.entity.User;
import com.ecommerce.app.payment.domain.core.exception.UserNotFoundException;
import com.ecommerce.app.payment.domain.core.exception.UserUnauthorized;
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
    private final GlobalVerifyJWT globalVerifyJWT;
    private final PaymentConfigData paymentConfigData;

    public UserHelper(UserRepository userRepository, UserDomainService userDomainService, GlobalVerifyJWT globalVerifyJWT, PaymentConfigData paymentConfigData) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.globalVerifyJWT = globalVerifyJWT;
        this.paymentConfigData = paymentConfigData;
    }

    /**
     * Check if user is active
     *
     * @param userId String
     * @return User
     */
    @Transactional
    public User checkActiveUser(String userId){
        User user = findUser(new UserId(UUID.fromString(userId)));
        userDomainService.checkActiveUser(user);
        return user;
    }

    /**
     * Find user by user id
     *
     * @param userId UserId
     * @return User
     */
    private User findUser(UserId userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            log.error("User not found");
            throw new UserNotFoundException("User not found");
        }
        return user.get();
    }

    /**
     * Get user from logged in
     *
     * @param authorizationHeader AuthorizationHeader
     * @return User
     */
    public User getUserFromLoggedIn(AuthorizationHeader authorizationHeader) {
        String token = authorizationHeader.getAuthorization().replace("Bearer ", "");;
        DecodedJWT decodedJWT = globalVerifyJWT.verifyJWT(token, paymentConfigData.getSecretKey());
        if (decodedJWT == null) {
            throw new UserUnauthorized("User not authorized");
        }
        return User.newBuilder()
                .withId(new UserId(UUID.fromString(decodedJWT.getSubject())))
                .withUserRole(UserRole.valueOf(decodedJWT.getClaim("role").asString()))
                .withEmail(decodedJWT.getClaim("email").asString())
                .withIsEmailVerified(decodedJWT.getClaim("is_email_verified").asBoolean())
                .withWarehouseId(decodedJWT.getClaim("warehouse_id").isNull() ? null : new WarehouseId(UUID.fromString(decodedJWT.getClaim("warehouse_id").asString())))
                .build();
    }
}
