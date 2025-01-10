package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.core.event.OrderShippedResponseEvent;
import com.ecommerce.app.warehouse.domain.core.event.OrderWarehouseResponseEvent;
import com.ecommerce.app.warehouse.domain.core.event.StockShippedUpdateEvent;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderPaidRequest;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderShippedRequest;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.order.OrderShippedResponseMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.order.OrderWarehouseResponseMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockShippedUpdateMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class OrderHandler {

    private final OrderHelper orderHelper;
    private final OrderWarehouseResponseMessagePublisher orderWarehouseResponseMessagePublisher;
    private final OrderShippedResponseMessagePublisher orderShippedResponseMessagePublisher;
    private final StockShippedUpdateMessagePublisher stockShippedUpdateMessagePublisher;

    public OrderHandler(OrderHelper orderHelper, OrderWarehouseResponseMessagePublisher orderWarehouseResponseMessagePublisher, OrderShippedResponseMessagePublisher orderShippedResponseMessagePublisher, StockShippedUpdateMessagePublisher stockShippedUpdateMessagePublisher) {
        this.orderHelper = orderHelper;
        this.orderWarehouseResponseMessagePublisher = orderWarehouseResponseMessagePublisher;
        this.orderShippedResponseMessagePublisher = orderShippedResponseMessagePublisher;
        this.stockShippedUpdateMessagePublisher = stockShippedUpdateMessagePublisher;
    }

    public void handleOrderPaid(OrderPaidRequest orderPaidRequest) {
        log.info("Handling order paid event for order id: {}", orderPaidRequest.getOrderId());
        OrderWarehouseResponseEvent orderWarehouseResponseEvent = orderHelper.processOrderPaidEvent(orderPaidRequest);
        orderWarehouseResponseMessagePublisher.publish(orderWarehouseResponseEvent);
    }

    public void handleOrderShipped(OrderShippedRequest orderShippedRequest) {
        log.info("Handling order shipped event for order id: {}", orderShippedRequest.getOrderId());
        Map<OrderShippedResponseEvent, StockShippedUpdateEvent> response = orderHelper.processOrderShipped(orderShippedRequest);
        response.forEach((orderShippedResponseEvent, stockShippedUpdateEvent) -> {
            orderShippedResponseMessagePublisher.publish(orderShippedResponseEvent);
            if (stockShippedUpdateEvent != null) {
                stockShippedUpdateMessagePublisher.publish(stockShippedUpdateEvent);
            }
        });
    }
}
