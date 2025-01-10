package com.ecommerce.app.payment.dataaccess.payment.mapper;

import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.payment.dataaccess.order.entity.OrderEntity;
import com.ecommerce.app.payment.dataaccess.payment.entity.PaymentEntity;
import com.ecommerce.app.payment.domain.core.entity.Order;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentDataAccessMapper {

    public Payment paymentEntityToPayment(PaymentEntity payment) {
        return Payment.newBuilder()
                .withId(new PaymentId(payment.getId()))
                .withOrder(Order.newBuilder()
                        .withId(new OrderId(payment.getOrder().getId()))
                        .withUserId(new UserId(payment.getOrder().getUserId()))
                        .withWarehouseId(new WarehouseId(payment.getOrder().getWarehouseId()))
                        .withStatus(payment.getOrder().getStatus())
                        .build())
                .withOrderId(new OrderId(payment.getOrder().getId()))
                .withAmount(new Money(payment.getAmount()))
                .withPaymentProof(payment.getPaymentProof())
                .withStatus(payment.getStatus())
                .withCretedAt(payment.getCreatedAt())
                .withUpdatedAt(payment.getUpdatedAt())
                .build();
    }

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .id(payment.getId().getValue())
                .amount(payment.getAmount().getAmount())
                .paymentProof(payment.getPaymentProof())
                .status(payment.getStatus())
                .createdAt(payment.getCretedAt())
                .updatedAt(payment.getUpdatedAt())
                .order(OrderEntity.builder()
                        .id(payment.getOrderId().getValue())
                        .userId(payment.getOrder().getUserId().getValue())
                        .warehouseId(payment.getOrder().getWarehouseId().getValue())
                        .status(payment.getOrder().getStatus())
                        .build())
                .build();
        return paymentEntity;
    }
}
