package com.ecommerce.app.user.application.service.ports.output.repository;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.application.service.dto.create.ListUserAddressResponse;
import com.ecommerce.app.user.domain.core.entity.UserAddress;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository {
    UserAddress save(UserAddress userAddress);
    Optional<UserAddress> findAddressByUserId(UserId userId);
    List<ListUserAddressResponse> findAllAddressesByUserId(UserId userId);
}
