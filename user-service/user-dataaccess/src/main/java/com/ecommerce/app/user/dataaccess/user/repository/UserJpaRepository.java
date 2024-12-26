package com.ecommerce.app.user.dataaccess.user.repository;

import com.ecommerce.app.user.dataaccess.user.entity.UserEntity;
import com.ecommerce.app.user.domain.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
