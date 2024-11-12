package com.ecommerce.app.user.messaging.mapper;

import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel;
import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseResponseAvroModel;
import com.ecommerce.app.user.application.service.dto.message.WarehouseCreate;
import com.ecommerce.app.user.application.service.dto.message.WarehouseResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMessagingDataMapper {

    public WarehouseCreate warehouseCreateAvroModelToWarehouseCreate(WarehouseRequestAvroModel warehouseRequestAvroModel) {
        return WarehouseCreate.builder()
                .warehouseId(warehouseRequestAvroModel.getWarehouseId().toString())
                .build();
    }

    public WarehouseResponse warehouseResponseAvroModelToWarehouseResponse(WarehouseResponseAvroModel warehouseResponseAvroModel) {
        return WarehouseResponse.builder()
                .warehouseId(warehouseResponseAvroModel.getWarehouseId().toString())
                .build();
    }
}
