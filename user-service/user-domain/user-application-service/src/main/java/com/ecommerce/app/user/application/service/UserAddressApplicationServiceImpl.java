package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.dto.create.CreateUserAddressCommand;
import com.ecommerce.app.user.application.service.dto.create.CreateUserAddressResponse;
import com.ecommerce.app.user.application.service.dto.create.ListUserAddressResponse;
import com.ecommerce.app.user.application.service.dto.create.UserIdQuery;
import com.ecommerce.app.user.application.service.ports.input.service.UserAddressApplicationService;
import com.ecommerce.app.user.domain.core.entity.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Validated
@Service
public class UserAddressApplicationServiceImpl implements UserAddressApplicationService {

    private final UserAddressHandler userAddressHelper;

    public UserAddressApplicationServiceImpl(UserAddressHandler userAddressHelper) {
        this.userAddressHelper = userAddressHelper;
    }

    @Override
    public CreateUserAddressResponse addAddress(CreateUserAddressCommand createUserAddressCommand) {
        return userAddressHelper.addAddressToUser(createUserAddressCommand);
    }

    @Override
    public List<ListUserAddressResponse> getListAddressByUserId(UserIdQuery userId) {
        return userAddressHelper.getListAddressByUserId(userId);
    }
}
