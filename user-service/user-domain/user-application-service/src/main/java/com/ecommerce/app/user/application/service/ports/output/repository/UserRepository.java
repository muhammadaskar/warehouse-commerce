package com.ecommerce.app.user.application.service.ports.output.repository;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.domain.core.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    List <User> findAll();
    Optional<User> findById(UserId userId);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
