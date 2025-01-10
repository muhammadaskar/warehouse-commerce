package com.ecommerce.app.order.application.service.ports.input.service;

import com.ecommerce.app.order.application.service.dto.create.*;
import com.ecommerce.app.order.application.service.dto.header.AuthorizationHeader;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand, AuthorizationHeader authorizationHeader);
    CreateOrderResponse shipOrder(@Valid CreateOrderIdCommand createOrderIdCommand, AuthorizationHeader authorizationHeader);
    CreateOrderResponse confirmOrder(@Valid CreateOrderIdCommand createOrderIdCommand, AuthorizationHeader authorizationHeader);
    List<OrderResponse> getOrdersByUserId(UserIdQuery userIdQuery, AuthorizationHeader authorizationHeader);
    List<OrderResponse> getOrdersByWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader);
    OrderResponse getOrderById(OrderIdQuery orderIdQuery, AuthorizationHeader authorizationHeader);
}
