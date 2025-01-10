package com.ecommerce.app.warehouse.domain.service.ports.output.repository;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.warehouse.domain.core.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId userId);
}
