package com.ecommerce.app.warehouse.domain.service.ports.input.message.listener.order;

import com.ecommerce.app.warehouse.domain.service.dto.message.OrderPaidRequest;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderShippedRequest;

public interface OrderApplicationMessageListener {
    void processOrder(OrderPaidRequest orderPaidRequest);
    void processShipping(OrderShippedRequest orderShippedRequest);
}
