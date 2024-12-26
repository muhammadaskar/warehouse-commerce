package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.dto.create.CreateUserAddressCommand;
import com.ecommerce.app.user.application.service.dto.create.CreateUserAddressResponse;
import com.ecommerce.app.user.application.service.dto.create.ListUserAddressResponse;
import com.ecommerce.app.user.application.service.dto.create.UserIdQuery;
import com.ecommerce.app.user.domain.core.entity.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class UserAddressHandler {
    private final UserAddressHelper userAddressHelper;

    public UserAddressHandler(UserAddressHelper userAddressHelper) {
        this.userAddressHelper = userAddressHelper;
    }

    public CreateUserAddressResponse addAddressToUser(CreateUserAddressCommand createUserAddressCommand) {
        log.info("Adding address to user with id: {}", createUserAddressCommand.getUserId());
        userAddressHelper.addAddressToUser(createUserAddressCommand);
        log.info("Address added to user with id: {}", createUserAddressCommand.getUserId());
        return CreateUserAddressResponse.builder()
                .addressId(UUID.randomUUID())
                .message("Address added successfully!")
                .build();
    }

    public List<ListUserAddressResponse> getListAddressByUserId(UserIdQuery userId) {
        log.info("Finding address by user id: {}", userId);
        List<ListUserAddressResponse> userAddresses = userAddressHelper.getListAddressByUserId(userId);
        return userAddresses;
    }
}
