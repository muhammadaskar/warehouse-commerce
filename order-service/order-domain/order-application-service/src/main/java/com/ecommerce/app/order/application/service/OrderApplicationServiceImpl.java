package com.ecommerce.app.order.application.service;

import com.ecommerce.app.order.application.service.dto.create.*;
import com.ecommerce.app.order.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.order.application.service.ports.input.service.OrderApplicationService;
import com.ecommerce.app.order.domain.core.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Validated
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderHandler orderHandler;

    public OrderApplicationServiceImpl(OrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand, AuthorizationHeader authorizationHeader) {
        return orderHandler.createOrder(createOrderCommand, authorizationHeader);
    }

    @Override
    public CreateOrderResponse shipOrder(CreateOrderIdCommand createOrderIdCommand, AuthorizationHeader authorizationHeader) {
        return orderHandler.shipOrder(createOrderIdCommand, authorizationHeader);
    }

    @Override
    public CreateOrderResponse confirmOrder(CreateOrderIdCommand createOrderIdCommand, AuthorizationHeader authorizationHeader) {
        return orderHandler.confirmOrder(createOrderIdCommand, authorizationHeader);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(UserIdQuery userIdQuery, AuthorizationHeader authorizationHeader) {
        return orderHandler.getOrdersByUserId(userIdQuery, authorizationHeader);
    }

    @Override
    public List<OrderResponse> getOrdersByWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader) {
        return orderHandler.getOrdersByWarehouseId(warehouseIdQuery, authorizationHeader);
    }

    @Override
    public OrderResponse getOrderById(OrderIdQuery orderIdQuery, AuthorizationHeader authorizationHeader) {
        return orderHandler.getOrderById(orderIdQuery, authorizationHeader);
    }
}
