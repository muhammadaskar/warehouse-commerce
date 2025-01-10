package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.payment.application.service.dto.message.*;
import com.ecommerce.app.payment.application.service.mapper.OrderDataMapper;
import com.ecommerce.app.payment.application.service.ports.output.OrderRepository;
import com.ecommerce.app.payment.application.service.ports.output.UserRepository;
import com.ecommerce.app.payment.domain.core.OrderDomainService;
import com.ecommerce.app.payment.domain.core.entity.Order;
import com.ecommerce.app.payment.domain.core.entity.User;
import com.ecommerce.app.payment.domain.core.exception.UserForbidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderHelper {

    private final OrderDomainService orderDomainService;
    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderHelper(OrderDomainService orderDomainService, OrderDataMapper orderDataMapper, OrderRepository orderRepository, UserRepository userRepository) {
        this.orderDomainService = orderDomainService;
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create order with payment request
     *
     * @param paymentRequest PaymentRequest
     * @return Order
     */
    @Transactional
    public Order createOrderPayment(PaymentRequest paymentRequest) {
        log.info("Creating order with id: {}", paymentRequest.getOrderId());

        Optional<User> user = userRepository.findById(new UserId(UUID.fromString(paymentRequest.getUserId())));
        if (user.isEmpty()) {
            throw new UserForbidden("User not found");
        }

        Order createdOrder = orderDataMapper.paymentRequestToOrder(paymentRequest);

        Optional<Order> findOrder = orderRepository.findById(createdOrder.getId());
        if (findOrder.isPresent()) {
            throw new RuntimeException("Order already exists");
        }

        orderDomainService.createOrder(user.get(), createdOrder);
        return saveOrder(createdOrder);
    }

    /**
     * Update order to pending
     *
     * @param paymentProofResponse PaymentProofResponse
     * @return Order
     */
    @Transactional
    public Order updateOrderToPending(PaymentProofResponse paymentProofResponse) {
        log.info("Updating order with id: {} to pending", paymentProofResponse.getOrderId());

        Order order = orderDataMapper.paymentProofResponseToOrder(paymentProofResponse);
        Order findOrder = findOrder(order.getId());

        orderDomainService.updateOrderToPendingPayment(findOrder);

        return saveOrder(findOrder);
    }

    /**
     * Update order to approved
     *
     * @param orderPaidRequest OrderPaidRequest
     * @return Order
     */
    @Transactional
    public Order updateOrderToApproved(OrderPaidRequest orderPaidRequest) {
        log.info("Updating order with id: {} to approved", orderPaidRequest.getOrderId());

        Order order = orderDataMapper.orderPaidRequestToOrder(orderPaidRequest);
        Order findOrder = findOrder(order.getId());

        orderDomainService.updateOrderToApprove(findOrder);

        return saveOrder(findOrder);
    }

    /**
     * Update order to processed status
     *
     * @param orderProcessedRequest OrderProcessedRequest
     * @return Order
     */
    public Order updateOrderToProcessed(OrderProcessedRequest orderProcessedRequest) {
        log.info("Updating order with id: {} to processed", orderProcessedRequest.getOrderId());

        Order order = orderDataMapper.orderProcessedRequestToOrder(orderProcessedRequest);
        Order findOrder = findOrder(order.getId());

        orderDomainService.updateOrderToProcessed(findOrder);

        return saveOrder(findOrder);
    }

    /**
     * Update order to shipped status
     *
     * @param orderShippedRequest OrderShippedRequest
     * @return Order
     */
    public Order updateOrderToShipped(OrderShippedRequest orderShippedRequest) {
        log.info("Updating order with id: {} to shipped", orderShippedRequest.getOrderId());

        Order order = orderDataMapper.orderShippedRequestToOrder(orderShippedRequest);
        Order findOrder = findOrder(order.getId());

        orderDomainService.updateOrderToShipped(findOrder);

        return saveOrder(findOrder);
    }

    /**
     * Update order to confirmed status
     *
     * @param orderConfirmedRequest OrderConfirmedRequest
     * @return Order
     */
    public Order updateOrderToConfirmed(OrderConfirmedRequest orderConfirmedRequest) {
        log.info("Updating order with id: {} to confirmed", orderConfirmedRequest.getOrderId());

        Order order = orderDataMapper.orderConfirmedRequestToOrder(orderConfirmedRequest);
        Order findOrder = findOrder(order.getId());

        orderDomainService.updateOrderToConfirmed(findOrder);

        return saveOrder(findOrder);
    }

    /**
     * Save order
     *
     * @param order Order
     * @return Order
     */
    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            throw new RuntimeException("Order not saved");
        }
        return orderResult;
    }

    /**
     * Find order
     *
     * @param orderId OrderId
     * @return Order
     */
    private Order findOrder(OrderId orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return order.get();
    }
}
