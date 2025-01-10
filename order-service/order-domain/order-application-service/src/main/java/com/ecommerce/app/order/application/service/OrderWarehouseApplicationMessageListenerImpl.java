package com.ecommerce.app.order.application.service;

import com.ecommerce.app.order.application.service.dto.message.OrderWarehouseResponse;
import com.ecommerce.app.order.application.service.ports.input.message.listener.warehouse.OrderWarehouseApplicationMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class OrderWarehouseApplicationMessageListenerImpl implements OrderWarehouseApplicationMessageListener {

    private final OrderHandler orderHandler;

    public OrderWarehouseApplicationMessageListenerImpl(OrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @Override
    public void processedOrder(OrderWarehouseResponse orderWarehouseResponse) {
        orderHandler.processedOrder(orderWarehouseResponse);
    }
}
