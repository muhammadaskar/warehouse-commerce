package com.ecommerce.app.user.dataaccess.user.repository;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.dataaccess.user.entity.UserAddressEntity;
import com.ecommerce.app.user.domain.core.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAddressJpaRepository extends JpaRepository<UserAddressEntity, UUID> {
    Optional<UserAddressEntity> findFirstAddressByUserId(UUID userId);
    List<UserAddressEntity> findAllAddressesByUserId(UUID userId);
}
