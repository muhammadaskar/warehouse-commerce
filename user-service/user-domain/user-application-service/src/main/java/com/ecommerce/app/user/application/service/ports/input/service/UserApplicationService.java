package com.ecommerce.app.user.application.service.ports.input.service;

import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminCommand;
import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminResponse;
import jakarta.validation.Valid;

public interface UserApplicationService {
    CreateWarehouseAdminResponse createWarehouseAdmin(@Valid CreateWarehouseAdminCommand command);
}
