package com.ecommerce.app.product.dataaccess.user.repository;

import com.ecommerce.app.product.dataaccess.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
}
