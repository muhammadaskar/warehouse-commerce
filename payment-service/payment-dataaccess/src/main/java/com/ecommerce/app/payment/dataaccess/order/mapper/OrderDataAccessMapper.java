package com.ecommerce.app.payment.dataaccess.order.mapper;

import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.payment.dataaccess.order.entity.OrderEntity;
import com.ecommerce.app.payment.dataaccess.payment.entity.PaymentEntity;
import com.ecommerce.app.payment.domain.core.entity.Order;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class OrderDataAccessMapper {

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.newBuilder()
                .withId(new OrderId(orderEntity.getId()))
                .withUserId(new UserId(orderEntity.getUserId()))
                .withWarehouseId(new WarehouseId(orderEntity.getWarehouseId()))
                .withStatus(orderEntity.getStatus())
                .withPayment(paymentEntityToPayment(orderEntity.getPayment()))
                .build();
    }

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .userId(order.getUserId().getValue())
                .payment(paymentToPaymentEntity(order.getPayment()))
                .warehouseId(order.getWarehouseId().getValue())
                .status(order.getStatus())
                .build();
        orderEntity.getPayment().setOrder(orderEntity);
        return orderEntity;
    }

    public Payment paymentEntityToPayment(PaymentEntity payment) {
        return Payment.newBuilder()
                .withId(new PaymentId(payment.getId()))
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
                .build();
//        paymentEntity.setOrder(orderToOrderEntity(payment.getOrder()));
        return paymentEntity;
    }
}
