package com.ecommerce.app.payment.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.common.domain.valueobject.OrderStatus;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.payment.domain.core.exception.OrderDomainException;

public class Order extends BaseEntity<OrderId> {
    private final UserId userId;
    private WarehouseId warehouseId;
    private OrderStatus status;

    private final Payment payment;

    public void initializeOrder() {
        validateOrder();
    }

    public void updateOrderToPendingPayment() {
        if (status != OrderStatus.AWAITING_PAYMENT) {
            throw new OrderDomainException("Order status must be AWAITING_PAYMENT");
        }
        status = OrderStatus.PENDING;
    }

    public void updateOrderToApprove() {
        if (status != OrderStatus.PENDING) {
            throw new OrderDomainException("Order status must be PENDING");
        }
        status = OrderStatus.APPROVED;
    }

    public void updateOrderToProcessed() {
        if (status != OrderStatus.APPROVED) {
            throw new OrderDomainException("Order status must be APPROVED");
        }
        status = OrderStatus.PROCESSED;
    }

    public void updateOrderToShipped() {
        if (status != OrderStatus.PROCESSED) {
            throw new OrderDomainException("Order status must be PROCESSED");
        }
        status = OrderStatus.SHIPPED;
    }

    public void updateOrderToConfirmed() {
        if (status != OrderStatus.SHIPPED) {
            throw new OrderDomainException("Order status must be SHIPPED");
        }
        status = OrderStatus.CONFIRMED;
    }

    private void validateOrder() {
        if (userId == null) {
            throw new OrderDomainException("User ID is required");
        }
        if (status == null) {
            throw new OrderDomainException("Order status is required");
        }

        if (status != OrderStatus.AWAITING_PAYMENT) {
            throw new OrderDomainException("Order status must be AWAITING_PAYMENT");
        }
    }

    private Order(Builder builder) {
        super.setId(builder.id);
        userId = builder.userId;
        warehouseId = builder.warehouseId;
        status = builder.status;
        payment = builder.payment;
    }

    public UserId getUserId() {
        return userId;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Payment getPayment() {
        return payment;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderId id;
        private UserId userId;
        private WarehouseId warehouseId;
        private OrderStatus status;

        private Payment payment;

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

        public Builder withWarehouseId(WarehouseId val) {
            warehouseId = val;
            return this;
        }

        public Builder withStatus(OrderStatus val) {
            status = val;
            return this;
        }

        public Builder withPayment(Payment val) {
            payment = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
