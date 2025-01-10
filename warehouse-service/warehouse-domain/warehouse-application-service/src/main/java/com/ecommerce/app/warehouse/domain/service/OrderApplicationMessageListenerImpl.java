package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.service.dto.message.OrderPaidRequest;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderShippedRequest;
import com.ecommerce.app.warehouse.domain.service.ports.input.message.listener.order.OrderApplicationMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class OrderApplicationMessageListenerImpl implements OrderApplicationMessageListener {

    private final OrderHandler orderHandler;

    public OrderApplicationMessageListenerImpl(OrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @Override
    public void processOrder(OrderPaidRequest orderPaidRequest) {
        orderHandler.handleOrderPaid(orderPaidRequest);
    }

    @Override
    public void processShipping(OrderShippedRequest orderShippedRequest) {
        orderHandler.handleOrderShipped(orderShippedRequest);
    }
}
