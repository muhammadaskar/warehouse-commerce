package com.ecommerce.app.user.application.service.mapper;

import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.application.service.dto.create.AddressItem;
import com.ecommerce.app.user.application.service.dto.create.CreateUserAddressCommand;
import com.ecommerce.app.user.application.service.dto.create.CreateUserAddressResponse;
import com.ecommerce.app.user.domain.core.entity.UserAddress;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAddressDataMapper {
    public UserAddress userAddressCommandToUserAddress(CreateUserAddressCommand createUserAddressCommand) {
        return UserAddress.builder()
                .withUserId(new UserId(UUID.fromString(createUserAddressCommand.getUserId())))
                .withAddress(userAddressToAddress(createUserAddressCommand.getAddress()))
                .build();
    }

    public CreateUserAddressResponse userAddressToCreateUserAddressResponse(UserAddress userAddress, String message) {
        return CreateUserAddressResponse.builder()
                .addressId(userAddress.getId().getValue())
                .message(message)
                .build();
    }

    private Address userAddressToAddress(AddressItem addressItem) {
        return new Address(
                addressItem.getStreet(),
                addressItem.getPostalCode(),
                addressItem.getCity(),
                addressItem.getLatitude(),
                addressItem.getLongitude()
        );
    }
}
