package com.ecommerce.app.order.application.rest;

import com.ecommerce.app.common.application.security.annotation.RequiresRole;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.order.application.service.dto.create.*;
import com.ecommerce.app.order.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.order.application.service.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @RequiresRole(UserRole.CUSTOMER)
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand, @RequestHeader("Authorization") AuthorizationHeader authorizationRequestHeader) {
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand, authorizationRequestHeader);
        return ResponseEntity.created(null).body(createOrderResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @PostMapping("/ship")
    public ResponseEntity<CreateOrderResponse> shipOrder(@RequestBody CreateOrderIdCommand createOrderIdCommand, @RequestHeader("Authorization") AuthorizationHeader authorizationRequestHeader) {
        CreateOrderResponse createOrderResponse = orderApplicationService.shipOrder(createOrderIdCommand, authorizationRequestHeader);
        return ResponseEntity.ok().body(createOrderResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.CUSTOMER})
    @PostMapping("/confirm")
    public ResponseEntity<CreateOrderResponse> confirmOrder(@RequestBody CreateOrderIdCommand createOrderIdCommand, @RequestHeader("Authorization") AuthorizationHeader authorizationRequestHeader) {
        CreateOrderResponse createOrderResponse = orderApplicationService.confirmOrder(createOrderIdCommand, authorizationRequestHeader);
        return ResponseEntity.ok().body(createOrderResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.CUSTOMER})
    @GetMapping(value = "user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable("userId") UUID userId, @RequestHeader("Authorization") AuthorizationHeader authorizationRequestHeader) {
        List<OrderResponse> orders = orderApplicationService.getOrdersByUserId(UserIdQuery.builder().userId(userId).build(), authorizationRequestHeader);
        return ResponseEntity.ok().body(orders);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @GetMapping(value = "warehouse/{warehouseId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByWarehouseId(@PathVariable("warehouseId") UUID warehouseId, @RequestHeader("Authorization") AuthorizationHeader authorizationRequestHeader) {
        List<OrderResponse> orders = orderApplicationService.getOrdersByWarehouseId(WarehouseIdQuery.builder().warehouseId(warehouseId).build(), authorizationRequestHeader);
        return ResponseEntity.ok().body(orders);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN, UserRole.CUSTOMER})
    @GetMapping(value = "/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") UUID orderId, @RequestHeader("Authorization") AuthorizationHeader authorizationRequestHeader) {
        OrderResponse order = orderApplicationService.getOrderById(OrderIdQuery.builder().orderId(orderId).build(), authorizationRequestHeader);
        return ResponseEntity.ok().body(order);
    }
}
