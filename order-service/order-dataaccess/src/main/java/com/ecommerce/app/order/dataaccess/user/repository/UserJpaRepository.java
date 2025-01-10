package com.ecommerce.app.order.dataaccess.user.repository;

import com.ecommerce.app.order.dataaccess.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
}
