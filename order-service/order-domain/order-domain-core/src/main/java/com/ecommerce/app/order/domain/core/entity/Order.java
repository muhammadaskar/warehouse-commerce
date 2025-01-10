package com.ecommerce.app.order.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.order.domain.core.exception.OrderException;
import com.ecommerce.app.order.domain.core.valueobject.OrderItemId;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

public class Order extends BaseEntity<OrderId> {
    private UserId userId;
    private final CartId cartId;
    private WarehouseId warehouseId;
    private final Address shippingAddress;
    private String shippingMethod;
    private Money shippingCost;
    private final Money totalPrice;
    private OrderStatus status;
    private final List<OrderItem> items;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    private Order(Builder builder) {
        super.setId(builder.id);
        userId = builder.userId;
        cartId = builder.cartId;
        warehouseId = builder.warehouseId;
        shippingAddress = builder.shippingAddress;
        shippingMethod = builder.shippingMethod;
        shippingCost = builder.shippingCost;
        totalPrice = builder.totalPrice;
        status = builder.status;
        items = builder.items;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public void initializeOrder(List<Warehouse> warehouses) {
        setId(new OrderId(UUID.randomUUID()));
        initializeOrderItems(warehouses);
        status = OrderStatus.AWAITING_PAYMENT;
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
        shippingMethod = "JNE";
        shippingCost = new Money(new BigDecimal("60000"));
    }

    private void initializeOrderItems(List<Warehouse> warehouses) {
        for (OrderItem item : items) {
            Warehouse warehouse = findNearestWarehouseWithPartialOrFullStock(warehouses, item);
            this.warehouseId = warehouse.getId();
            item.initializeOrderItem(super.getId(), new OrderItemId(UUID.randomUUID().toString()));
        }
    }

    public void payOrder() {
        if (status != OrderStatus.PENDING) {
            throw new OrderException("Order is not in correct state for payment");
        }
        status = OrderStatus.APPROVED;
        updatedAt = ZonedDateTime.now();
    }

    public void processedOrder() {
        if (status != OrderStatus.APPROVED) {
            throw new OrderException("Order is not in correct state for processing");
        }
        status = OrderStatus.PROCESSED;
        updatedAt = ZonedDateTime.now();
    }

    public void shippedOrder() {
        if (status != OrderStatus.PROCESSED) {
            throw new OrderException("Order is not in correct state for shipping");
        }
        status = OrderStatus.SHIPPED;
        updatedAt = ZonedDateTime.now();
    }

    public void confirmOrder() {
        if (status != OrderStatus.SHIPPED) {
            throw new OrderException("Order is not in correct state for confirmation");
        }
        status = OrderStatus.CONFIRMED;
        updatedAt = ZonedDateTime.now();
    }

    public void cancelOrder() {
        status = OrderStatus.CANCELLED;
        updatedAt = ZonedDateTime.now();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to kilometers

        return distance;
    }

    private Warehouse findNearestWarehouseWithPartialOrFullStock(List<Warehouse> warehouses, OrderItem item) {
        Warehouse nearestWarehouse = null;
        Warehouse backupWarehouse = null;
        double minDistance = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAvailableStock(item.getProductId()) > 0) {
                double distance = calculateDistance(
                        Double.parseDouble(this.shippingAddress.getLatitude()),
                        Double.parseDouble(this.shippingAddress.getLongitude()),
                        Double.parseDouble(warehouse.getAddress().getLatitude()),
                        Double.parseDouble(warehouse.getAddress().getLongitude())
                );

                if (distance < minDistance) {
                    minDistance = distance;
                    nearestWarehouse = warehouse;
                }
            }
        }

        if (nearestWarehouse == null || item.getQuantity() > nearestWarehouse.getAvailableStock(item.getProductId())) {
            // Find other warehouses with stock
            List<Warehouse> otherWarehouses = findOtherWarehousesWithStock(warehouses, item.getProductId().getValue(), item.getQuantity());
            if (otherWarehouses.isEmpty()) {
                throw new OrderException("No warehouse with sufficient stock found");
            }

            // make sure other warehouses are sorted by distance
            for (Warehouse warehouse : otherWarehouses) {
                if (nearestWarehouse.equals(warehouse)) {
                    continue; // // skip if the warehouse is the nearestWarehouse
                }

                // calculate total stock from nearestWarehouse and current warehouse
                int totalAvailableStock = nearestWarehouse.getAvailableStock(item.getProductId())
                        + warehouse.getAvailableStock(item.getProductId());

                System.out.printf("Nearest Warehouse ID: %s, Current Warehouse ID: %s, Total Available Stock: %d%n",
                        nearestWarehouse.getId().getValue(), warehouse.getId().getValue(), totalAvailableStock);

                // check if total stock is enough
                if (totalAvailableStock >= item.getQuantity()) {
                    backupWarehouse = warehouse;
                    break; // stop if found
                }
            }

            if (backupWarehouse == null) {
                throw new OrderException("No warehouse with sufficient stock found");
            }
        }

        return nearestWarehouse;
    }

    private List<Warehouse> findOtherWarehousesWithStock(List<Warehouse> warehouses, UUID productId, int requiredQuantity) {
        List<Warehouse> backupWarehouses = new ArrayList<>();

        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAvailableStock(new ProductId(productId)) > 0) {
                backupWarehouses.add(warehouse);
            }
        }

        backupWarehouses.sort(Comparator.comparingDouble(w -> calculateDistance(
                Double.parseDouble(this.shippingAddress.getLatitude()),
                Double.parseDouble(this.shippingAddress.getLongitude()),
                Double.parseDouble(w.getAddress().getLatitude()),
                Double.parseDouble(w.getAddress().getLongitude())
        )));

        return backupWarehouses;
    }

    public void validateOrder() {
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateTotalPrice() {
        if (totalPrice == null || !totalPrice.isGreaterThanZero()) {
            throw new OrderException("Order total price is invalid");
        }
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO, Money::add);

        if (!totalPrice.equals(orderItemsTotal)){
            throw new OrderException("Order total price does not match with items total price");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderException("Order item price is invalid");
        }
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public void awaitingPaymentToPending() {
        if (status != OrderStatus.AWAITING_PAYMENT) {
            throw new OrderException("Order is not in correct state for payment");
        }
        status = OrderStatus.PENDING;
        updatedAt = ZonedDateTime.now();
    }

    public UserId getUserId() {
        return userId;
    }

    public CartId getCartId() {
        return cartId;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public Money getShippingCost() {
        return shippingCost;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderId id;
        private UserId userId;
        private CartId cartId;
        private WarehouseId warehouseId;
        private Address shippingAddress;
        private String shippingMethod;
        private Money shippingCost;
        private Money totalPrice;
        private OrderStatus status;
        private List<OrderItem> items;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(OrderId val) {
            id = val;
            return this;
        }

        public Builder withUserId(UserId val) {
            userId = val;
            return this;
        }

        public Builder withCartId(CartId val) {
            cartId = val;
            return this;
        }

        public Builder withWarehouseId(WarehouseId val) {
            warehouseId = val;
            return this;
        }

        public Builder withShippingAddress(Address val) {
            shippingAddress = val;
            return this;
        }

        public Builder withShippingMethod(String val) {
            shippingMethod = val;
            return this;
        }

        public Builder withShippingCost(Money val) {
            shippingCost = val;
            return this;
        }

        public Builder withTotalPrice(Money val) {
            totalPrice = val;
            return this;
        }

        public Builder withStatus(OrderStatus val) {
            status = val;
            return this;
        }

        public Builder withItems(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder withCreatedAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder withUpdatedAt(ZonedDateTime val) {
            updatedAt = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
