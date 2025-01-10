package com.ecommerce.app.product.application.service.ports.output;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.product.domain.core.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByID(UserId userId);
}
