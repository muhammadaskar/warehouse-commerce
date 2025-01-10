package com.ecommerce.app.payment.application.service.ports.output;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.payment.domain.core.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId userId);
}
