package com.ecommerce.app.order.dataaccess.order.mapper;

import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.order.dataaccess.order.entity.OrderAddressEntity;
import com.ecommerce.app.order.dataaccess.order.entity.OrderEntity;
import com.ecommerce.app.order.dataaccess.order.entity.OrderItemEntity;
import com.ecommerce.app.order.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.order.domain.core.entity.Order;
import com.ecommerce.app.order.domain.core.entity.OrderItem;
import com.ecommerce.app.order.domain.core.entity.Product;
import com.ecommerce.app.order.domain.core.valueobject.OrderItemId;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .userId(order.getUserId().getValue())
                .cartId(order.getCartId().getValue())
                .warehouseId(order.getWarehouseId().getValue())
                .shippingAddress(orderAddressToOrderAddressEntity(order.getShippingAddress()))
                .shippingMethod(order.getShippingMethod())
                .shippingCost(order.getShippingCost().getAmount())
                .totalPrice(order.getTotalPrice().getAmount())
                .items(orderItemsToOrderItemEntities(order.getItems()))
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
        orderEntity.getShippingAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.newBuilder()
                .withId(new OrderId(orderEntity.getId()))
                .withUserId(new UserId(orderEntity.getUserId()))
                .withCartId(new CartId(orderEntity.getCartId()))
                .withWarehouseId(new WarehouseId(orderEntity.getWarehouseId()))
                .withShippingMethod(orderEntity.getShippingMethod())
                .withShippingCost(new Money(orderEntity.getShippingCost()))
                .withTotalPrice(new Money(orderEntity.getTotalPrice()))
                .withStatus(orderEntity.getStatus())
                .withCreatedAt(orderEntity.getCreatedAt())
                .withUpdatedAt(orderEntity.getUpdatedAt())
                .withShippingAddress(addressEntityToShippingAddress(orderEntity.getShippingAddress()))
                .withItems(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .build();
    }

    public OrderEntity orderUpdateToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .userId(order.getUserId().getValue())
                .status(order.getStatus())
                .shippingMethod(order.getShippingMethod())
                .shippingCost(order.getShippingCost().getAmount())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
        return orderEntity;
    }

    public Order orderEntityToOrderUpdate(OrderEntity orderEntity) {
        return Order.newBuilder()
                .withId(new OrderId(orderEntity.getId()))
                .withUserId(new UserId(orderEntity.getUserId()))
                .withStatus(orderEntity.getStatus())
                .withShippingAddress(addressEntityToShippingAddress(orderEntity.getShippingAddress()))
                .withShippingMethod(orderEntity.getShippingMethod())
                .withShippingCost(new Money(orderEntity.getShippingCost()))
                .withCreatedAt(orderEntity.getCreatedAt())
                .withUpdatedAt(orderEntity.getUpdatedAt())
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream()
                .map(orderItemEntity -> OrderItem.newBuilder()
                        .withId(new OrderItemId(orderItemEntity.getId()))
                        .withProduct(productEntityToProduct(orderItemEntity.getProduct()))
                        .withProductId(new ProductId(orderItemEntity.getProduct().getId()))
                        .withQuantity(orderItemEntity.getQuantity())
                        .withPrice(new Money(orderItemEntity.getPrice()))
                        .withSubTotal(new Money(orderItemEntity.getSubTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private Address addressEntityToShippingAddress(OrderAddressEntity address) {
        return new Address(
                address.getStreet(),
                address.getPostalCode(),
                address.getCity(),
                address.getLatitude(),
                address.getLongitude()
        );
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(
            List<com.ecommerce.app.order.domain.core.entity.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem ->
                        OrderItemEntity.builder()
                                .id(orderItem.getId().getValue())
                                .product(productToProductEntity(orderItem.getProduct()))
                                .price(orderItem.getPrice().getAmount())
                                .quantity(orderItem.getQuantity())
                                .subTotal(orderItem.getSubTotal().getAmount())
                                .build())
                .collect(Collectors.toList());
    }

    private OrderAddressEntity orderAddressToOrderAddressEntity(Address address) {
        return OrderAddressEntity.builder()
                .id(UUID.randomUUID())
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();
    }

    public ProductEntity productToProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId().getValue())
                .sku(product.getSku())
                .price(product.getPrice().getAmount())
                .build();
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.newBuilder()
                .withId(new ProductId(productEntity.getId()))
                .withSku(productEntity.getSku())
                .withPrice(new Money(productEntity.getPrice()))
                .build();
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(
            List<com.ecommerce.app.order.domain.core.entity.OrderItem> orderItems,
            Map<UUID, ProductEntity> productEntityMap) {
        return orderItems.stream()
                .map(orderItem -> {
                    ProductEntity productEntity = productEntityMap.get(orderItem.getProductId().getValue());
                    if (productEntity == null) {
                        throw new IllegalArgumentException("Product not found for ID: " + orderItem.getProductId().getValue());
                    }
                    return OrderItemEntity.builder()
                            .id(orderItem.getId().getValue())
                            .product(productEntity)
                            .price(orderItem.getPrice().getAmount())
                            .quantity(orderItem.getQuantity())
                            .subTotal(orderItem.getSubTotal().getAmount())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public OrderEntity orderToOrderEntity(Order order, Map<UUID, ProductEntity> productEntityMap) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .userId(order.getUserId().getValue())
                .cartId(order.getCartId().getValue())
                .warehouseId(order.getWarehouseId().getValue())
                .shippingAddress(orderAddressToOrderAddressEntity(order.getShippingAddress()))
                .shippingMethod(order.getShippingMethod())
                .shippingCost(order.getShippingCost().getAmount())
                .totalPrice(order.getTotalPrice().getAmount())
                .items(orderItemsToOrderItemEntities(order.getItems(), productEntityMap))
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
        orderEntity.getShippingAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }
}
