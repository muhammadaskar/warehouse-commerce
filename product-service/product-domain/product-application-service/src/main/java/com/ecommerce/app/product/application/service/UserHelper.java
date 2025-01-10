package com.ecommerce.app.product.application.service;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.product.application.service.ports.output.UserRepository;
import com.ecommerce.app.product.domain.core.UserDomainService;
import com.ecommerce.app.product.domain.core.entity.User;
import com.ecommerce.app.product.domain.core.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class UserHelper {

    private final UserDomainService userDomainService;
    private final UserRepository userRepository;

    public UserHelper(UserDomainService userDomainService, UserRepository userRepository) {
        this.userDomainService = userDomainService;
        this.userRepository = userRepository;
    }

    /**
     * Check active user
     *
     * @param userId String
     * @return User
     */
    @Transactional(readOnly = true)
    public User checkActiveUser(String userId) {
        log.info("Finding user by id: {}", userId);
        User user = userRepository.findByID(new UserId(UUID.fromString(userId)))
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " could not be found!"));
        userDomainService.checkActiveUser(user);
        return user;
    }
}
