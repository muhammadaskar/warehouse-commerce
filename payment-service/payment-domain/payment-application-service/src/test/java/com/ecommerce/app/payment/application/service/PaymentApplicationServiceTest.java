package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.payment.application.service.dto.create.CreatePaymentApproveCommand;
import com.ecommerce.app.payment.application.service.dto.create.CreatePaymentApproveResponse;
import com.ecommerce.app.payment.application.service.dto.create.CreatePaymentProofCommand;
import com.ecommerce.app.payment.application.service.dto.create.CreatePaymentProofResponse;
import com.ecommerce.app.payment.application.service.dto.message.PaymentRequest;
import com.ecommerce.app.payment.application.service.mapper.OrderDataMapper;
import com.ecommerce.app.payment.application.service.mapper.PaymentDataMapper;
import com.ecommerce.app.payment.application.service.ports.input.message.listener.payment.PaymentRequestMessageListener;
import com.ecommerce.app.payment.application.service.ports.input.service.PaymentApplicationService;
import com.ecommerce.app.payment.application.service.ports.output.OrderRepository;
import com.ecommerce.app.payment.application.service.ports.output.PaymentRepository;
import com.ecommerce.app.payment.application.service.ports.output.UserRepository;
import com.ecommerce.app.payment.domain.core.entity.Order;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import com.ecommerce.app.payment.domain.core.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = PaymentTestConfiguration.class)
@ExtendWith(OutputCaptureExtension.class)
public class PaymentApplicationServiceTest {

    @Autowired
    private PaymentApplicationService paymentApplicationService;

    @Autowired
    private PaymentRequestMessageListener paymentRequestMessageListener;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private PaymentDataMapper paymentDataMapper;

    private AuthorizationHeader authorizationHeader;
    private AuthorizationHeader authorizationHeaderSuperAdmin;
    private AuthorizationHeader authorizationHeaderWarehouseAdmin1;
    private PaymentRequest paymentRequestMessage;
    private CreatePaymentProofCommand createPaymentProofCommand;
    private CreatePaymentApproveCommand createPaymentApproveCommand;

    private final UUID PAYMENT_ID = UUID.randomUUID();
    private final UUID ORDER_ID = UUID.randomUUID();
    private final UUID CUSTOMER_ID = UUID.fromString("444e4567-e89b-12d3-a456-426614174003");
    private final UUID SUPER_ADMIN_ID = UUID.fromString("111e4567-e89b-12d3-a456-426614174000");
    private final UUID WAREHOUSE_ADMIN_ID1 = UUID.fromString("222e4567-e89b-12d3-a456-426614174001");
    private final UUID WAREHOUSE_ID = UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529");

    // Authorization token for customer
    private final String AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0NDRlNDU2Ny1lODliLTEyZDMtYTQ1Ni00MjY2MTQxNzQwMDMiLCJlbWFpbCI6ImN1c3RvbWVyQGdtYWlsLmNvbSIsInJvbGUiOiJDVVNUT01FUiIsImlzX2VtYWlsX3ZlcmlmaWVkIjp0cnVlLCJ3YXJlaG91c2VfaWQiOm51bGwsImlhdCI6MTczNjIwMzc1OCwiZXhwIjoxNzY3NzM5NzU4fQ.1vFwvLFvAIfc_XH6zwRs6xHt8Zdkmjdr_hr9dSa4_Hk";

    // Authorization token for super admin
    private final String AUTHORIZATION_SUPER_ADMIN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMTFlNDU2Ny1lODliLTEyZDMtYTQ1Ni00MjY2MTQxNzQwMDAiLCJlbWFpbCI6InN1cGVyYWRtaW5AZ21haWwuY29tIiwicm9sZSI6IlNVUEVSX0FETUlOIiwiaXNfZW1haWxfdmVyaWZpZWQiOnRydWUsIndhcmVob3VzZV9pZCI6bnVsbCwiaWF0IjoxNzM2MjkwMDc1LCJleHAiOjE3Njc4MjYwNzV9.Vc2vsZlQQ74b7CAdblq7ztAKpJJyJyu6mR0orfAiF_w";

    // Authorization token for warehouse admin 1
    private final String AUTHORIZATION_WAREHOUSE_ADMIN1 = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyMjJlNDU2Ny1lODliLTEyZDMtYTQ1Ni00MjY2MTQxNzQwMDEiLCJlbWFpbCI6IndhcmVob3VzZWFkbWluMUBnbWFpbC5jb20iLCJyb2xlIjoiV0FSRUhPVVNFX0FETUlOIiwiaXNfZW1haWxfdmVyaWZpZWQiOnRydWUsIndhcmVob3VzZV9pZCI6IjhiNjIyYWJlLWI0YzYtNDU0MC04NGRmLTdkYzBlOTc1MjUyOSIsImlhdCI6MTczNjI5MDYxNCwiZXhwIjoxNzY3ODI2NjE0fQ.-zhhRawQB6O5gjlEyEImMHbS8ATVvfB6hmPz3KrCqow";

    private Order order;
    private Payment payment;
    private User user;
    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeAll
    public void init() {
        authorizationHeader = AuthorizationHeader.builder()
                .authorization(AUTHORIZATION)
                .build();

        authorizationHeaderSuperAdmin = AuthorizationHeader.builder()
                .authorization(AUTHORIZATION_SUPER_ADMIN)
                .build();

        authorizationHeaderWarehouseAdmin1 = AuthorizationHeader.builder()
                .authorization(AUTHORIZATION_WAREHOUSE_ADMIN1)
                .build();

        paymentRequestMessage = PaymentRequest.builder()
                .id(PAYMENT_ID.toString())
                .userId(CUSTOMER_ID.toString())
                .warehouseId(WAREHOUSE_ID.toString())
                .orderId(ORDER_ID.toString())
                .price(new BigDecimal(5500000))
                .createdAt(Instant.now())
                .paymentStatus(PaymentStatus.AWAITING_PAYMENT)
                .build();

        createPaymentProofCommand = CreatePaymentProofCommand.builder()
                .orderId(ORDER_ID)
                .paymentProof("paymentProof")
                .build();

        createPaymentApproveCommand = CreatePaymentApproveCommand.builder()
                .paymentId(PAYMENT_ID)
                .build();
    }

    @Test
    void testCreateOrderPayment(CapturedOutput output) {
        System.out.println("Test createOrderPayment");
        order = orderDataMapper.paymentRequestToOrder(paymentRequestMessage);
        order.setId(new OrderId(ORDER_ID));

        payment = orderDataMapper.paymentRequestToPayment(paymentRequestMessage);
        payment.setId(new PaymentId(PAYMENT_ID));
        user = User.newBuilder()
                .withId(new UserId(CUSTOMER_ID))
                .withEmail("customer@gmail.com")
                .withWarehouseId(null)
                .withIsEmailVerified(true)
                .withUserRole(UserRole.CUSTOMER)
                .build();

        Mockito.when(userRepository.findById(new UserId(CUSTOMER_ID))).thenReturn(java.util.Optional.of(user));
        Mockito.when(orderRepository.findById(new OrderId(ORDER_ID))).thenReturn(Optional.empty());
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);

        paymentRequestMessageListener.createOrderPayment(paymentRequestMessage);
        Mockito.verify(orderRepository).save(order);

        // Assert log output
        assertTrue(output.getOut().contains("Creating order with id: " + ORDER_ID));
        assertTrue(output.getOut().contains("Order created with id: " + ORDER_ID));
        assertTrue(output.getOut().contains("Order Payment created successfully!"));
    }

    @Test
    void testUploadPaymentProof(CapturedOutput output) {
        System.out.println("Test uploadPaymentProof");
        Payment paymentMapper = paymentDataMapper.createPaymentProofToPayment(createPaymentProofCommand);

        user = User.newBuilder()
                .withId(new UserId(CUSTOMER_ID))
                .withEmail("customer@gmail.com")
                .withWarehouseId(null)
                .withIsEmailVerified(true)
                .withUserRole(UserRole.CUSTOMER)
                .build();

        order = Order.newBuilder()
                .withId(new OrderId(ORDER_ID))
                .withUserId(new UserId(CUSTOMER_ID))
                .withWarehouseId(new WarehouseId(WAREHOUSE_ID))
                .withStatus(OrderStatus.AWAITING_PAYMENT)
                .build();

        payment = Payment.newBuilder()
                .withId(new PaymentId(PAYMENT_ID))
                .withOrder(order)
                .withOrderId(new OrderId(ORDER_ID))
                .withAmount(new Money(new BigDecimal(5500000)))
                .withStatus(PaymentStatus.AWAITING_PAYMENT)
                .build();

        Mockito.when(userRepository.findById(new UserId(CUSTOMER_ID))).thenReturn(java.util.Optional.of(user));
        Mockito.when(paymentRepository.findByOrderId(new OrderId(order.getId().getValue()))).thenReturn(java.util.Optional.of(payment));
        Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(paymentMapper);

        CreatePaymentProofResponse response =  paymentApplicationService.createPaymentProof(createPaymentProofCommand, authorizationHeader);

        assertTrue(output.getOut().contains("Creating payment proof for order with id: " + ORDER_ID));
        assertEquals("Payment proof uploaded successfully", response.getMessage(), "Payment Message should be 'Payment proof uploaded successfully'");
    }

    @Test
    void testApprovePayment(CapturedOutput output) {
        System.out.println("Test approvePayment");
        Payment paymentMapper = paymentDataMapper.approvePaymentToPayment(createPaymentApproveCommand);

        user = User.newBuilder()
                .withId(new UserId(WAREHOUSE_ADMIN_ID1))
                .withEmail("warehouseadmin1@gmail.com")
                .withWarehouseId(new WarehouseId(WAREHOUSE_ID))
                .withIsEmailVerified(true)
                .withUserRole(UserRole.WAREHOUSE_ADMIN)
                .build();

        order = Order.newBuilder()
                .withId(new OrderId(ORDER_ID))
                .withUserId(new UserId(CUSTOMER_ID))
                .withWarehouseId(new WarehouseId(WAREHOUSE_ID))
                .withStatus(OrderStatus.PENDING)
                .build();

        payment = Payment.newBuilder()
                .withId(new PaymentId(PAYMENT_ID))
                .withOrder(order)
                .withOrderId(new OrderId(ORDER_ID))
                .withAmount(new Money(new BigDecimal(5500000)))
                .withStatus(PaymentStatus.UNDER_REVIEW)
                .build();

        Mockito.when(userRepository.findById(new UserId(SUPER_ADMIN_ID))).thenReturn(java.util.Optional.of(user));
        Mockito.when(paymentRepository.findById(new PaymentId(payment.getId().getValue()))).thenReturn(java.util.Optional.of(payment));
        Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(paymentMapper);

        CreatePaymentApproveResponse response =  paymentApplicationService.approvePayment(createPaymentApproveCommand, authorizationHeaderWarehouseAdmin1);

        assertTrue(output.getOut().contains("Payment with id: " + payment.getId().getValue() + " has been approved"));
        assertEquals("Payment approved successfully", response.getMessage(), "Payment Message should be 'Payment approved successfully'");
    }
}
