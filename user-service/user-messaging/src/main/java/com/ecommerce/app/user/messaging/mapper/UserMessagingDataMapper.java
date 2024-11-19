package com.ecommerce.app.user.messaging.mapper;

import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseAdminCreateAvroModel;
import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseCreateAvroModel;
import com.ecommerce.app.user.application.service.dto.message.WarehouseCreate;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.event.WarehouseAdminCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserMessagingDataMapper {

    public WarehouseAdminCreateAvroModel warehouseAdminCreatedEventToWarehouseAdminCreateAvroModel(WarehouseAdminCreatedEvent warehouseAdminCreatedEvent) {
        User user = warehouseAdminCreatedEvent.getUser();
        return WarehouseAdminCreateAvroModel.newBuilder()
                .setUserId(user.getId().getValue())
                .setUsername(user.getUsername())
                .build();
    }

    public WarehouseCreate warehouseCreateAvroModelToWarehouseCreate(WarehouseCreateAvroModel warehouseCreateAvroModel) {
        return WarehouseCreate.builder().warehouseId(warehouseCreateAvroModel.getWarehouseId().toString()).build();
    }
}
