package com.ecommerce.app.payment.messaging.mapper;

import com.ecommerce.app.common.domain.valueobject.OrderStatus;
import com.ecommerce.app.kafka.warehouse.avro.model.*;
import com.ecommerce.app.payment.application.service.dto.message.*;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import com.ecommerce.app.payment.domain.core.event.PaymentApprovedEvent;
import com.ecommerce.app.payment.domain.core.event.PaymentProofUploadedEvent;
import com.ecommerce.app.common.domain.valueobject.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PaymentMessagingDataMapper {

    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId().toString())
                .userId(paymentRequestAvroModel.getUserId().toString())
                .warehouseId(paymentRequestAvroModel.getWarehouseId().toString())
                .orderId(paymentRequestAvroModel.getOrderId().toString())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentStatus(PaymentStatus.valueOf(paymentRequestAvroModel.getPaymentOrderStatus().toString()))
                .build();
    }

    public PaymentProofUploadAvroModel paymentProofUploadEventToPaymentProofUploadAvroModel(PaymentProofUploadedEvent paymentProofUploadEvent) {
        Payment payment = paymentProofUploadEvent.getPayment();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(payment.getCretedAt().toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return PaymentProofUploadAvroModel.newBuilder()
                .setPaymentId(payment.getId().getValue())
                .setOrderId(payment.getOrderId().getValue())
                .setPaymentOrderStatus(PaymentOrderStatus.valueOf(payment.getStatus().toString()))
                .setCreatedAt(zonedDateTime.toInstant())
                .build();
    }

    public PaymentProofResponse paymentProofResponseAvroModelToPaymentProofResponse(PaymentProofResponseAvroModel paymentProofResponseAvroModel) {
        return PaymentProofResponse.builder()
                .orderId(paymentProofResponseAvroModel.getOrderId().toString())
                .orderStatus(paymentProofResponseAvroModel.getOrderStatus().toString())
                .build();
    }

    public PaymentApprovedRequestAvroModel paymentApprovedEventToPaymentApprovedRequestAvroModel(PaymentApprovedEvent paymentApprovedEvent) {
        Payment payment = paymentApprovedEvent.getPayment();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(payment.getCretedAt().toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return PaymentApprovedRequestAvroModel.newBuilder()
                .setPaymentId(payment.getId().getValue())
                .setOrderId(payment.getOrderId().getValue())
                .setPaymentOrderStatus(PaymentOrderStatus.valueOf(payment.getStatus().toString()))
                .setCreatedAt(zonedDateTime.toInstant())
                .build();
    }

    public OrderPaidRequest orderPaidRequestAvroModelToOrderPaidRequest(OrderPaidRequestAvroModel orderPaidRequestAvroModel) {
        return OrderPaidRequest.builder()
                .orderId(orderPaidRequestAvroModel.getOrderId().toString())
                .orderStatus(orderPaidRequestAvroModel.getOrderStatus().toString())
                .build();
    }

    public OrderProcessedRequest orderProcessedRequestAvroModelToOrderProcessedRequest(OrderProcessedRequestAvroModel orderProcessedRequestAvroModel) {
        return OrderProcessedRequest.builder()
                .orderId(orderProcessedRequestAvroModel.getOrderId().toString())
                .orderStatus(orderProcessedRequestAvroModel.getOrderStatus().toString())
                .build();
    }

    public OrderShippedRequest orderShippedRequestAvroModelToOrderShippedRequest(OrderShippedRequestAvroModel orderShippedRequestAvroModel) {
        return OrderShippedRequest.builder()
                .orderId(orderShippedRequestAvroModel.getOrderId().toString())
                .warehouseId(orderShippedRequestAvroModel.getWarehouseId().toString())
                .orderStatus(orderShippedRequestAvroModel.getOrderStatus().toString())
                .build();
    }

    public OrderConfirmedRequest orderConfirmedRequestAvroModelToOrderConfirmedRequest(OrderConfirmedRequestAvroModel orderConfirmedRequestAvroModel) {
        return OrderConfirmedRequest.builder()
                .orderId(orderConfirmedRequestAvroModel.getOrderId().toString())
                .warehouseId(orderConfirmedRequestAvroModel.getWarehouseId().toString())
                .orderStatus(orderConfirmedRequestAvroModel.getOrderStatus().toString())
                .build();
    }
}
