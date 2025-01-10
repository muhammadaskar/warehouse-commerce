package com.ecommerce.app.order.application.service.ports.output;

import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.order.domain.core.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId orderId);
    List<Order> findAllByUserId(UserId userId);
    List<Order> findAllByWarehouseId(WarehouseId warehouseId);
}
