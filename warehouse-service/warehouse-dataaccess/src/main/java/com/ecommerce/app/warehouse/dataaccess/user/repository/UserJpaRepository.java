package com.ecommerce.app.warehouse.dataaccess.user.repository;

import com.ecommerce.app.warehouse.dataaccess.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
}
