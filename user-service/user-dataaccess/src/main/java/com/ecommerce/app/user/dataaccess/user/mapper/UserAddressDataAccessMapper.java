package com.ecommerce.app.user.dataaccess.user.mapper;

import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.application.service.dto.create.AddressItem;
import com.ecommerce.app.user.application.service.dto.create.ListUserAddressResponse;
import com.ecommerce.app.user.dataaccess.user.entity.UserAddressEntity;
import com.ecommerce.app.user.domain.core.entity.UserAddress;
import com.ecommerce.app.user.domain.core.valueobject.AddressId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAddressDataAccessMapper {
    public UserAddressEntity addressToAddressEntity(UserAddress userAddress) {
        return UserAddressEntity.builder()
                .id(userAddress.getId().getValue())
                .userId(userAddress.getUserId().getValue())
                .street(userAddress.getAddress().getStreet())
                .postalCode(userAddress.getAddress().getPostalCode())
                .city(userAddress.getAddress().getCity())
                .latitude(userAddress.getAddress().getLatitude())
                .longitude(userAddress.getAddress().getLongitude())
                .isPrimary(userAddress.isPrimary())
                .build();
    }

    public ListUserAddressResponse addressEntityToListUserAddressResponse(UserAddressEntity userAddressEntity) {
        return ListUserAddressResponse.builder()
                .id(UUID.fromString(userAddressEntity.getId().toString()))
                .userId(UUID.fromString(userAddressEntity.getUserId().toString()))
                .isPrimary(userAddressEntity.isPrimary())
                .address(addressEntityToAddressItem(userAddressEntity))
                .build();
    }

    public UserAddress addressEntityToAddress(UserAddressEntity userAddressEntity) {
        return UserAddress.builder()
                .withId(new AddressId(userAddressEntity.getId()))
                .withUserId(new UserId(userAddressEntity.getUserId()))
                .withAddress(userAddressEntityToAddress(userAddressEntity))
                .build();
    }

    private Address userAddressEntityToAddress(UserAddressEntity userAddressEntity) {
        return new Address(
                userAddressEntity.getStreet(),
                userAddressEntity.getPostalCode(),
                userAddressEntity.getCity(),
                userAddressEntity.getLatitude(),
                userAddressEntity.getLongitude()
        );
    }

    private AddressItem addressEntityToAddressItem(UserAddressEntity userAddressEntity) {
        return AddressItem.builder()
                .street(userAddressEntity.getStreet())
                .postalCode(userAddressEntity.getPostalCode())
                .city(userAddressEntity.getCity())
                .latitude(userAddressEntity.getLatitude())
                .longitude(userAddressEntity.getLongitude())
                .build();
    }
}
