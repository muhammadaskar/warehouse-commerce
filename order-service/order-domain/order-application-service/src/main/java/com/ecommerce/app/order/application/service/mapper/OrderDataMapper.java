package com.ecommerce.app.order.application.service.mapper;

import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.order.application.service.dto.create.*;
import com.ecommerce.app.order.application.service.dto.message.OrderWarehouseResponse;
import com.ecommerce.app.order.application.service.dto.message.PaymentApprovedRequest;
import com.ecommerce.app.order.application.service.dto.message.PaymentProofUploadRequest;
import com.ecommerce.app.order.application.service.dto.message.StockTransferredUpdate;
import com.ecommerce.app.order.domain.core.entity.Order;
import com.ecommerce.app.order.domain.core.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.newBuilder()
                .withUserId(new UserId(createOrderCommand.getUserId()))
                .withCartId(new CartId(createOrderCommand.getCartId()))
                .withTotalPrice(new Money(createOrderCommand.getTotalPrice()))
                .withItems(orderItemsToOrderItems(createOrderCommand.getItems()))
                .withShippingAddress(orderAddressToAddress(createOrderCommand.getShippingAddress()))
                .build();
    }

    public List<OrderItem> orderItemsToOrderItems(List<com.ecommerce.app.order.application.service.dto.create.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> OrderItem.newBuilder()
                        .withProductId(new ProductId(orderItem.getProductId()))
                        .withQuantity(orderItem.getQuantity())
                        .withPrice(new Money(orderItem.getPrice()))
                        .withSubTotal(new Money(orderItem.getSubTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    public Address orderAddressToAddress(OrderAddress address) {
        return new Address(
                address.getStreet(),
                address.getPostalCode(),
                address.getCity(),
                address.getLatitude(),
                address.getLongitude()
        );
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderId(order.getId().getValue())
                .orderStatus(order.getStatus().toString())
                .message(message)
                .build();
    }

    public List<OrderResponse> ordersToOrderResponses(List<Order> orders) {
        return orders.stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getId().getValue())
                        .userId(order.getUserId().getValue())
                        .cartId(order.getCartId().getValue())
                        .totalPrice(order.getTotalPrice().getAmount().floatValue())
                        .items(orderItemsToOrderItemsResponse(order.getItems()))
                        .shippingAddress(orderAddressToOrderAddress(order.getShippingAddress()))
                        .orderStatus(order.getStatus().toString())
                        .createdAt(order.getCreatedAt().toInstant())
                        .updatedAt(order.getUpdatedAt().toInstant())
                        .build())
                .collect(Collectors.toList());
    }

    public OrderResponse orderToOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId().getValue())
                .userId(order.getUserId().getValue())
                .cartId(order.getCartId().getValue())
                .totalPrice(order.getTotalPrice().getAmount().floatValue())
                .items(orderItemsToOrderItemsResponse(order.getItems()))
                .shippingAddress(orderAddressToOrderAddress(order.getShippingAddress()))
                .orderStatus(order.getStatus().toString())
                .createdAt(order.getCreatedAt().toInstant())
                .updatedAt(order.getUpdatedAt().toInstant())
                .build();
    }

    private OrderAddress orderAddressToOrderAddress(Address address) {
        return OrderAddress.builder()
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();
    }

    public Order paymentProofUploadedToOrder(PaymentProofUploadRequest paymentProofUploaded) {
        return Order.newBuilder()
                .withId(new OrderId(UUID.fromString(paymentProofUploaded.getOrderId())))
                .withStatus(OrderStatus.PENDING)
                .build();
    }

    public Order paymentApprovedToOrder(PaymentApprovedRequest paymentApprovedRequest) {
        return Order.newBuilder()
                .withId(new OrderId(UUID.fromString(paymentApprovedRequest.getOrderId())))
                .build();
    }

    public Order orderWarehouseResponseToOrder(OrderWarehouseResponse orderWarehouseResponse) {
        return Order.newBuilder()
                .withId(new OrderId(UUID.fromString(orderWarehouseResponse.getOrderId())))
                .withWarehouseId(new WarehouseId(UUID.fromString(orderWarehouseResponse.getWarehouseId())))
                .withStatus(orderWarehouseResponse.getOrderStatus())
                .build();
    }

    public Order createOrderIdCommandToOrder(CreateOrderIdCommand createOrderIdCommand) {
        return Order.newBuilder()
                .withId(new OrderId(createOrderIdCommand.getOrderId()))
                .build();
    }

    private List<OrderItemResponse> orderItemsToOrderItemsResponse(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> OrderItemResponse.builder()
                        .id(orderItem.getId().getValue())
                        .product(orderItemToProductResponse(orderItem))
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPrice().getAmount().floatValue())
                        .subTotal(orderItem.getSubTotal().getAmount().floatValue())
                        .build())
                .collect(Collectors.toList());
    }

    private ProductResponse orderItemToProductResponse(OrderItem orderItem) {
        return ProductResponse.builder()
                .productId(orderItem.getProduct().getId().getValue())
                .sku(orderItem.getProduct().getSku())
                .build();
    }
}
