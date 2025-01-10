package com.ecommerce.app.order.application.service.ports.input.message.listener.warehouse;

import com.ecommerce.app.order.application.service.dto.message.OrderWarehouseResponse;

public interface OrderWarehouseApplicationMessageListener {
    void processedOrder(OrderWarehouseResponse orderWarehouseResponse);
}
