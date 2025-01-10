package com.ecommerce.app.order.application.service;

import com.ecommerce.app.order.application.service.dto.create.*;
import com.ecommerce.app.order.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.order.application.service.dto.message.OrderWarehouseResponse;
import com.ecommerce.app.order.application.service.dto.message.PaymentApprovedRequest;
import com.ecommerce.app.order.application.service.dto.message.PaymentProofUploadRequest;
import com.ecommerce.app.order.application.service.mapper.OrderDataMapper;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderConfirmedRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderProcessedRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderShippedRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderPaidEventRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderPaymentProofUploadedResponseMessagePublisher;
import com.ecommerce.app.order.domain.core.entity.Order;
import com.ecommerce.app.order.domain.core.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OrderHandler {

    private final OrderHelper orderHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;
    private final OrderPaymentProofUploadedResponseMessagePublisher orderPaymentProofUploadedResponseMessagePublisher;
    private final OrderPaidEventRequestMessagePublisher orderPaidEventRequestMessagePublisher;
    private final OrderProcessedRequestMessagePublisher orderProcessedRequestMessagePublisher;
    private final OrderShippedRequestMessagePublisher orderShippedRequestMessagePublisher;
    private final OrderConfirmedRequestMessagePublisher orderConfirmedRequestMessagePublisher;

    public OrderHandler(OrderHelper orderHelper, OrderDataMapper orderDataMapper, OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher, OrderPaymentProofUploadedResponseMessagePublisher orderPaymentProofUploadedResponseMessagePublisher, OrderPaidEventRequestMessagePublisher orderPaidEventRequestMessagePublisher, OrderProcessedRequestMessagePublisher orderProcessedRequestMessagePublisher, OrderShippedRequestMessagePublisher orderShippedRequestMessagePublisher, OrderConfirmedRequestMessagePublisher orderConfirmedRequestMessagePublisher) {
        this.orderHelper = orderHelper;
        this.orderDataMapper = orderDataMapper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
        this.orderPaymentProofUploadedResponseMessagePublisher = orderPaymentProofUploadedResponseMessagePublisher;
        this.orderPaidEventRequestMessagePublisher = orderPaidEventRequestMessagePublisher;
        this.orderProcessedRequestMessagePublisher = orderProcessedRequestMessagePublisher;
        this.orderShippedRequestMessagePublisher = orderShippedRequestMessagePublisher;
        this.orderConfirmedRequestMessagePublisher = orderConfirmedRequestMessagePublisher;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand, AuthorizationHeader authorizationHeader) {
        log.info("Creating order for user with id: {}", createOrderCommand.getUserId());
        OrderCreatedEvent orderCreatedEvent = orderHelper.persistOrder(createOrderCommand, authorizationHeader);
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        log.info("Order created for user with id: {}", createOrderCommand.getUserId());
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(), "Order created successfully!");
    }

    public void paymentProofUploaded(PaymentProofUploadRequest paymentProofUploadRequest) {
        log.info("Payment proof uploaded for order with id: {}", paymentProofUploadRequest.getOrderId());
        OrderPaymentProofUploadedEvent orderPaymentProofUploadedEvent =  orderHelper.paymentProofUploaded(paymentProofUploadRequest);
        orderPaymentProofUploadedResponseMessagePublisher.publish(orderPaymentProofUploadedEvent);
    }

    public void updateOrderToApproved(PaymentApprovedRequest paymentApprovedRequest) {
        log.info("Payment approved for order with id: {}", paymentApprovedRequest.getOrderId());
        OrderPaidEvent orderPaidEvent =  orderHelper.payOrder(paymentApprovedRequest);
        orderPaidEventRequestMessagePublisher.publish(orderPaidEvent);
    }

    public void processedOrder(OrderWarehouseResponse orderWarehouseResponse) {
        log.info("Order with id: {} is being processed", orderWarehouseResponse.getOrderId());
        OrderProcessedEvent orderProcessedEvent = orderHelper.processedOrder(orderWarehouseResponse);
        orderProcessedRequestMessagePublisher.publish(orderProcessedEvent);
    }

    public CreateOrderResponse shipOrder(CreateOrderIdCommand createOrderIdCommand, AuthorizationHeader authorizationHeader) {
        log.info("Order with id: {} is being shipped", createOrderIdCommand.getOrderId());
        OrderShippedEvent orderShippedEvent = orderHelper.shippedOrder(createOrderIdCommand, authorizationHeader);
        orderShippedRequestMessagePublisher.publish(orderShippedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderShippedEvent.getOrder(), "Order shipped successfully!");
    }

    public CreateOrderResponse confirmOrder(CreateOrderIdCommand createOrderIdCommand, AuthorizationHeader authorizationHeader) {
        log.info("Order with id: {} is being confirmed", createOrderIdCommand.getOrderId());
        OrderConfirmedEvent orderConfirmedEvent = orderHelper.confirmOrder(createOrderIdCommand, authorizationHeader);
        orderConfirmedRequestMessagePublisher.publish(orderConfirmedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderConfirmedEvent.getOrder(), "Order confirmed successfully!");
    }

    public List<OrderResponse> getOrdersByUserId(UserIdQuery userIdQuery, AuthorizationHeader authorizationHeader) {
        log.info("Getting orders for user with id: {}", userIdQuery.getUserId());
        return orderDataMapper.ordersToOrderResponses(orderHelper.getOrdersByUserId(userIdQuery, authorizationHeader));
    }

    public List<OrderResponse> getOrdersByWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader) {
        log.info("Getting orders for warehouse with id: {}", warehouseIdQuery.getWarehouseId());
        return orderDataMapper.ordersToOrderResponses(orderHelper.getOrdersByWarehouseId(warehouseIdQuery, authorizationHeader));
    }

    public OrderResponse getOrderById(OrderIdQuery orderIdQuery, AuthorizationHeader authorizationHeader) {
        log.info("Getting order with id: {}", orderIdQuery.getOrderId());
        return orderDataMapper.orderToOrderResponse(orderHelper.getOrderById(orderIdQuery, authorizationHeader));
    }
}
