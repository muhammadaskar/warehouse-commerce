package com.ecommerce.app.order.application.service;

import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.order.application.service.dto.create.*;
import com.ecommerce.app.order.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.order.application.service.dto.message.OrderWarehouseResponse;
import com.ecommerce.app.order.application.service.dto.message.PaymentApprovedRequest;
import com.ecommerce.app.order.application.service.dto.message.PaymentProofUploadRequest;
import com.ecommerce.app.order.application.service.mapper.OrderDataMapper;
import com.ecommerce.app.order.application.service.ports.output.OrderRepository;
import com.ecommerce.app.order.application.service.ports.output.ProductRepository;
import com.ecommerce.app.order.application.service.ports.output.WarehouseRepository;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderConfirmedRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderProcessedRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderShippedRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderPaidEventRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderPaymentProofUploadedResponseMessagePublisher;
import com.ecommerce.app.order.domain.core.OrderDomainService;
import com.ecommerce.app.order.domain.core.entity.Order;
import com.ecommerce.app.order.domain.core.entity.Product;
import com.ecommerce.app.order.domain.core.entity.User;
import com.ecommerce.app.order.domain.core.entity.Warehouse;
import com.ecommerce.app.order.domain.core.event.*;
import com.ecommerce.app.order.domain.core.exception.OrderException;
import com.ecommerce.app.order.domain.core.exception.OrderNotFoundException;
import com.ecommerce.app.order.domain.core.exception.UserUnauthorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class OrderHelper {

    private final OrderDomainService orderDomainService;
    private final UserHelper userHelper;
    private final WarehouseRepository warehouseRepository;
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;
    private final ProductHelper productHelper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;
    private final OrderPaymentProofUploadedResponseMessagePublisher orderPaymentProofUploadedResponseMessagePublisher;
    private final OrderPaidEventRequestMessagePublisher orderPaidEventRequestMessagePublisher;
    private final OrderProcessedRequestMessagePublisher orderProcessedRequestMessagePublisher;
    private final OrderShippedRequestMessagePublisher orderShippedRequestMessagePublisher;
    private final OrderConfirmedRequestMessagePublisher orderConfirmedRequestMessagePublisher;

    public OrderHelper(OrderDomainService orderDomainService, UserHelper userHelper, WarehouseRepository warehouseRepository, OrderRepository orderRepository, OrderDataMapper orderDataMapper, ProductHelper productHelper, OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher, OrderPaymentProofUploadedResponseMessagePublisher orderPaymentProofUploadedResponseMessagePublisher, OrderPaidEventRequestMessagePublisher orderPaidEventRequestMessagePublisher, OrderProcessedRequestMessagePublisher orderProcessedRequestMessagePublisher, OrderShippedRequestMessagePublisher orderShippedRequestMessagePublisher, OrderConfirmedRequestMessagePublisher orderConfirmedRequestMessagePublisher) {
        this.orderDomainService = orderDomainService;
        this.userHelper = userHelper;
        this.warehouseRepository = warehouseRepository;
        this.orderRepository = orderRepository;
        this.orderDataMapper = orderDataMapper;
        this.productHelper = productHelper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
        this.orderPaymentProofUploadedResponseMessagePublisher = orderPaymentProofUploadedResponseMessagePublisher;
        this.orderPaidEventRequestMessagePublisher = orderPaidEventRequestMessagePublisher;
        this.orderProcessedRequestMessagePublisher = orderProcessedRequestMessagePublisher;
        this.orderShippedRequestMessagePublisher = orderShippedRequestMessagePublisher;
        this.orderConfirmedRequestMessagePublisher = orderConfirmedRequestMessagePublisher;
    }

    /**
     * Create order and send response to payment service
     * @param createOrderCommand createOrderCommand
     * @param authorizationHeader authorizationHeader
     * @return OrderCreatedEvent
     */
    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand, AuthorizationHeader authorizationHeader) {
        log.info("Creating order for user with id: {}", createOrderCommand.getUserId());
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setUserId(userHelper.getUserFromLoggedIn(authorizationHeader).getId());

        checkProductStock(order);
        List<Warehouse> warehouses = getWarehouses();

        OrderCreatedEvent orderCreatedEvent = orderDomainService.createOrder(order, warehouses, orderCreatedPaymentRequestMessagePublisher);
        saveOrder(order);
        return orderCreatedEvent;
    }

    /**
     * Update order status if customer uploaded payment proof though payment service
     * @param paymentProofUploadRequest paymentProofUploadRequest
     * @return OrderPaymentProofUploadedEvent
     */
    @Transactional
    public OrderPaymentProofUploadedEvent paymentProofUploaded(PaymentProofUploadRequest paymentProofUploadRequest) {
        log.info("Change Status order to pending with id: {}", paymentProofUploadRequest.getOrderId());
        Order order = orderDataMapper.paymentProofUploadedToOrder(paymentProofUploadRequest);
        Order findOrder = orderRepository.findById(order.getId()).orElseThrow(() -> new OrderException("Order not found"));
        OrderPaymentProofUploadedEvent orderPaymentProofUploadedEvent = orderDomainService.updateOrderPaymentStatus(findOrder, orderPaymentProofUploadedResponseMessagePublisher);
        orderRepository.save(findOrder);
        return orderPaymentProofUploadedEvent;
    }

    /**
     * Update order status to paid if payment is approved by warehouse admin or super admin in payment service
     * @param paymentApprovedRequest paymentApprovedRequest
     * @return OrderPaidEvent
     */
    @Transactional
    public OrderPaidEvent payOrder(PaymentApprovedRequest paymentApprovedRequest) {
        log.info("Change Status order to paid with id: {}", paymentApprovedRequest.getOrderId());
        Order order = orderDataMapper.paymentApprovedToOrder(paymentApprovedRequest);
        Order findOrder = findOrder(order.getId());
        OrderPaidEvent orderPaidEvent = orderDomainService.updateOrderStatusToPaid(findOrder, orderPaidEventRequestMessagePublisher);
        orderRepository.save(findOrder);
        return orderPaidEvent;
    }

    /**
     * Process order, send response to warehouse service and payment service
     * @param orderWarehouseResponse orderWarehouseResponse
     * @return OrderProcessedEvent
     */
    @Transactional
    public OrderProcessedEvent processedOrder(OrderWarehouseResponse orderWarehouseResponse) {
        Order order = orderDataMapper.orderWarehouseResponseToOrder(orderWarehouseResponse);
        Order findOrder = findOrder(order.getId());
        OrderProcessedEvent orderProcessedEvent =  orderDomainService.processOrder(findOrder, orderProcessedRequestMessagePublisher);
        orderRepository.save(findOrder);
        return orderProcessedEvent;
    }

    /**
     * Ship order and send response to warehouse service, payment service
     * @param createOrderIdCommand createOrderIdCommand
     * @param authorizationHeader authorizationHeader
     * @return OrderShippedEvent
     */
    @Transactional
    public OrderShippedEvent shippedOrder(CreateOrderIdCommand createOrderIdCommand, AuthorizationHeader authorizationHeader) {
        Order order = orderDataMapper.createOrderIdCommandToOrder(createOrderIdCommand);
        Order findOrder = findOrder(order.getId());

        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getRole().equals(UserRole.WAREHOUSE_ADMIN) && !findOrder.getWarehouseId().equals(user.getWarehouseId())) {
            throw new UserUnauthorized("Warehouse admin is not authorized to ship order");
        }

        OrderShippedEvent orderShippedEvent =  orderDomainService.shipOrder(findOrder, orderShippedRequestMessagePublisher);
        orderRepository.save(findOrder);
        return orderShippedEvent;
    }

    /**
     * Confirm order and send response to payment service
     * @param createOrderIdCommand createOrderIdCommand
     * @param authorizationHeader authorizationHeader
     * @return OrderConfirmedEvent
     */
    @Transactional
    public OrderConfirmedEvent confirmOrder(CreateOrderIdCommand createOrderIdCommand, AuthorizationHeader authorizationHeader) {
        Order order = orderDataMapper.createOrderIdCommandToOrder(createOrderIdCommand);
        Order findOrder = findOrder(order.getId());

        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (!findOrder.getUserId().equals(user.getId())) {
            throw new UserUnauthorized("User is not authorized to confirm order");
        }

        OrderConfirmedEvent orderConfirmedEvent =  orderDomainService.confirmOrder(findOrder, orderConfirmedRequestMessagePublisher);
        orderRepository.save(findOrder);
        return orderConfirmedEvent;
    }

    /**
     * Get orders by user id
     * @param userIdQuery userIdQuery
     * @param authorizationHeader authorizationHeader
     * @return List<Order>
     */
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(UserIdQuery userIdQuery, AuthorizationHeader authorizationHeader) {
        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getRole().equals(UserRole.CUSTOMER) &&
                !user.getId().equals(new UserId(userIdQuery.getUserId()))) {
            throw new UserUnauthorized("User is not authorized to get orders");
        }

        return orderRepository.findAllByUserId(new UserId(userIdQuery.getUserId()));
    }

    /**
     * Get orders by warehouse id
     * @param warehouseIdQuery warehouseIdQuery
     * @param authorizationHeader authorizationHeader
     * @return List<Order>
     */
    @Transactional(readOnly = true)
    public List<Order> getOrdersByWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader) {
        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getRole().equals(UserRole.WAREHOUSE_ADMIN) &&
                !user.getWarehouseId().equals(new WarehouseId(warehouseIdQuery.getWarehouseId()))) {
            throw new UserUnauthorized("Warehouse admin is not authorized to get orders");
        }

        return orderRepository.findAllByWarehouseId(new WarehouseId(warehouseIdQuery.getWarehouseId()));
    }

    /**
     * Get order by order id
     * @param orderIdQuery orderIdQuery
     * @param authorizationHeader authorizationHeader
     * @return Order
     */
    @Transactional(readOnly = true)
    public Order getOrderById(OrderIdQuery orderIdQuery, AuthorizationHeader authorizationHeader) {
        Order findOrder = findOrder(new OrderId(orderIdQuery.getOrderId()));

        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getRole().equals(UserRole.CUSTOMER) &&
                !findOrder.getUserId().equals(user.getId())) {
            throw new UserUnauthorized("User is not authorized to get order");
        }

        if (user.getRole().equals(UserRole.WAREHOUSE_ADMIN) &&
                !findOrder.getWarehouseId().equals(user.getWarehouseId())) {
            throw new UserUnauthorized("Warehouse admin is not authorized to get order");
        }

        return findOrder;
    }

    /**
     * Check if product stock is available based on order items
     * @param order order
     */
    private void checkProductStock(Order order) {
        order.getItems().parallelStream().forEach(orderItem -> {
            Product product = productHelper.findProduct(orderItem.getProductId());
            orderItem.isPriceValid(product);
        });
    }

    /**
     * Get all warehouses
     * @return List<Warehouse>
     */
    private List<Warehouse> getWarehouses() {
        return warehouseRepository.findAllWithProductsAndStocks();
    }

    /**
     * Save order
     * @param order order
     * @throws OrderException OrderException
     */
    private void saveOrder(Order order) {
        Order orderResult =  orderRepository.save(order);
        if (orderResult == null) {
            throw new OrderException("Order not saved");
        }
    }

    /**
     * Find order by order id
     * @param orderId orderId
     * @return Order
     * @throws OrderNotFoundException OrderNotFoundException
     */
    private Order findOrder(OrderId orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }
        return order.get();
    }
}
