package com.ecommerce.app.user.application.service.ports.input.service;

import com.ecommerce.app.user.application.service.dto.create.CreateUserAddressCommand;
import com.ecommerce.app.user.application.service.dto.create.CreateUserAddressResponse;
import com.ecommerce.app.user.application.service.dto.create.ListUserAddressResponse;
import com.ecommerce.app.user.application.service.dto.create.UserIdQuery;
import com.ecommerce.app.user.domain.core.entity.UserAddress;

import java.util.List;

public interface UserAddressApplicationService {
    CreateUserAddressResponse addAddress(CreateUserAddressCommand createUserAddressCommand);
    List<ListUserAddressResponse> getListAddressByUserId(UserIdQuery userIdQuery);
}
