package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.dto.message.WarehouseCreate;
import com.ecommerce.app.user.application.service.ports.input.message.listener.warehouse.WarehouseResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@Slf4j
public class WarehouseResponseMessageListenerImpl implements WarehouseResponseMessageListener {
    private final WarehouseSaga warehouseSaga;

    public WarehouseResponseMessageListenerImpl(WarehouseSaga warehouseSaga) {
        this.warehouseSaga = warehouseSaga;
    }

    @Override
    public void warehouseCreated(WarehouseCreate warehouseCreate) {
        warehouseSaga.process(warehouseCreate);
    }
}
