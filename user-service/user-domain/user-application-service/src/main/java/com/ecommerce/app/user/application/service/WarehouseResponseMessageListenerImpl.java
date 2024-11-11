package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.ports.input.message.listener.warehouse.WarehouseResponseMessageListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class WarehouseResponseMessageListenerImpl implements WarehouseResponseMessageListener {
    @Override
    public void warehouseCreated() {
    }
}
