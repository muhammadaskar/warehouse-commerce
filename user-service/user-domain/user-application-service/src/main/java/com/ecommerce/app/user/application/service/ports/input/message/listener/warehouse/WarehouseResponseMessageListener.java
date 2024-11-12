package com.ecommerce.app.user.application.service.ports.input.message.listener.warehouse;

import com.ecommerce.app.user.application.service.dto.message.WarehouseCreate;

public interface WarehouseResponseMessageListener  {
    void warehouseCreated(WarehouseCreate warehouseCreate);
}
