package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminCommand;
import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminResponse;
import com.ecommerce.app.user.application.service.ports.input.service.UserApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class UserApplicationServiceImpl implements UserApplicationService {
    private final WarehouseAdminCreateHandler warehouseAdminCreateHandler;

    public UserApplicationServiceImpl(WarehouseAdminCreateHandler warehouseAdminCreateHandler) {
        this.warehouseAdminCreateHandler = warehouseAdminCreateHandler;
    }

    @Override
    public CreateWarehouseAdminResponse createWarehouseAdmin(CreateWarehouseAdminCommand command) {
        return warehouseAdminCreateHandler.createWarehouseAdmin(command);
    }
}
