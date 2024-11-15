package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminCommand;
import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminResponse;
import com.ecommerce.app.user.application.service.mapper.UserDataMapper;
import com.ecommerce.app.user.application.service.ports.output.message.publisher.user.WarehouseAdminCreatedMessagePublisher;
import com.ecommerce.app.user.domain.core.event.WarehouseAdminCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarehouseAdminCreateHandler {
    private final WarehouseAdminCreateHelper warehouseAdminCreateHelper;
    private final UserDataMapper userDataMapper;
    private final WarehouseAdminCreatedMessagePublisher warehouseAdminCreatedMessagePublisher;

    public WarehouseAdminCreateHandler(WarehouseAdminCreateHelper warehouseAdminCreateHelper, UserDataMapper userDataMapper, WarehouseAdminCreatedMessagePublisher warehouseAdminCreatedMessagePublisher) {
        this.warehouseAdminCreateHelper = warehouseAdminCreateHelper;
        this.userDataMapper = userDataMapper;
        this.warehouseAdminCreatedMessagePublisher = warehouseAdminCreatedMessagePublisher;
    }

    public CreateWarehouseAdminResponse createWarehouseAdmin(CreateWarehouseAdminCommand createWarehouseAdminCommand) {
        WarehouseAdminCreatedEvent warehouseAdminCreatedEvent = warehouseAdminCreateHelper.persistWarehouseAdmin(createWarehouseAdminCommand);
        warehouseAdminCreatedMessagePublisher.publish(warehouseAdminCreatedEvent);
        return userDataMapper.warehouseAdminToCreateWarehouseAdminResponse(
                warehouseAdminCreatedEvent.getUser(), "warehouse admin created"
        );
    }
}
