package com.ecommerce.app.payment.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.payment.domain.core.exception.PaymentDomainException;
import com.ecommerce.app.payment.domain.core.exception.UserForbidden;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Payment extends BaseEntity<PaymentId> {
    private final OrderId orderId;
    private final Money amount;
    private String paymentProof;
    private PaymentStatus status;
    private final ZonedDateTime cretedAt = ZonedDateTime.now();
    private final ZonedDateTime updatedAt = ZonedDateTime.now();
    private final Order order;

    private Payment(Builder builder) {
        super.setId(builder.id);
        orderId = builder.orderId;
        amount = builder.amount;
        paymentProof = builder.paymentProof;
        status = builder.status;
        order = builder.order;
    }

    public void initializePaymentRequest() {
        validatePaymentRequest();
        super.setId(new PaymentId(UUID.randomUUID()));
    }

    private void validatePaymentRequest() {
        if (orderId == null) {
            throw new PaymentDomainException("Order Id is required");
        }
        if (amount == null) {
            throw new PaymentDomainException("Amount is required");
        }
    }

    public void updatePaymentProof(User user, String paymentProof) {
        if (!validateSameUserId(user)) {
            throw new UserForbidden("User forbidden");
        }

        if (status != PaymentStatus.AWAITING_PAYMENT) {
            throw new PaymentDomainException("Payment proof can be updated only for pending payment");
        }
        this.paymentProof = paymentProof;
        this.status = PaymentStatus.UNDER_REVIEW;
    }

    public void approvePayment(User user) {
        if (!validateSameWarehouseIdAdmin(user)) {
            throw new UserForbidden("User forbidden");
        }

        if (status != PaymentStatus.UNDER_REVIEW) {
            throw new PaymentDomainException("Payment can be approved only if it is under review");
        }
        this.status = PaymentStatus.PAID;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Money getAmount() {
        return amount;
    }

    public String getPaymentProof() {
        return paymentProof;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public ZonedDateTime getCretedAt() {
        return cretedAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    private boolean validateSameWarehouseIdAdmin(User user) {
        return user.getUserRole().equals(UserRole.WAREHOUSE_ADMIN) && order.getWarehouseId().equals(user.getWarehouseId());
    }

    private boolean validateSameUserId(User user) {
        return order.getUserId().equals(user.getId());
    }

    public Order getOrder() {
        return order;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private PaymentId id;
        private OrderId orderId;
        private Money amount;
        private String paymentProof;
        private PaymentStatus status;
        private ZonedDateTime cretedAt;
        private ZonedDateTime updatedAt;
        private Order order;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(PaymentId val) {
            id = val;
            return this;
        }

        public Builder withOrderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder withAmount(Money val) {
            amount = val;
            return this;
        }

        public Builder withPaymentProof(String val) {
            paymentProof = val;
            return this;
        }

        public Builder withStatus(PaymentStatus val) {
            status = val;
            return this;
        }

        public Builder withCretedAt(ZonedDateTime val) {
            cretedAt = val;
            return this;
        }

        public Builder withUpdatedAt(ZonedDateTime val) {
            updatedAt = val;
            return this;
        }

        public Builder withOrder(Order val) {
            order = val;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
