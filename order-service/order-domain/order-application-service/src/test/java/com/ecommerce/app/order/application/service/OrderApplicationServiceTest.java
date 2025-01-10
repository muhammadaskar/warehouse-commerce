package com.ecommerce.app.order.application.service;

import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.order.application.service.dto.create.*;
import com.ecommerce.app.order.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.order.application.service.dto.message.OrderWarehouseResponse;
import com.ecommerce.app.order.application.service.dto.message.PaymentApprovedRequest;
import com.ecommerce.app.order.application.service.dto.message.PaymentProofUploadRequest;
import com.ecommerce.app.order.application.service.mapper.OrderDataMapper;
import com.ecommerce.app.order.application.service.ports.input.message.listener.warehouse.OrderWarehouseApplicationMessageListener;
import com.ecommerce.app.order.application.service.ports.input.service.OrderApplicationService;
import com.ecommerce.app.order.application.service.ports.output.OrderRepository;
import com.ecommerce.app.order.application.service.ports.output.ProductRepository;
import com.ecommerce.app.order.application.service.ports.output.WarehouseRepository;
import com.ecommerce.app.order.domain.core.entity.Order;
import com.ecommerce.app.order.domain.core.entity.Product;
import com.ecommerce.app.order.domain.core.entity.Stock;
import com.ecommerce.app.order.domain.core.entity.Warehouse;
import com.ecommerce.app.order.domain.core.exception.OrderException;
import org.junit.jupiter.api.Assertions;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
@ExtendWith(OutputCaptureExtension.class)
public class OrderApplicationServiceTest {

    @Autowired
    OrderApplicationService orderApplicationService;

    @Autowired
    OrderWarehouseApplicationMessageListener orderWarehouseApplicationMessageListener;

    @Autowired
    PaymentApplicationMessageListener paymentApplicationMessageListener;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private AuthorizationHeader authorizationHeader;
    private AuthorizationHeader authorizationHeaderWarehouseAdmin1;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandFailedPrice;
    private CreateOrderIdCommand createOrderIdCommand;

    // from event message
    private PaymentProofUploadRequest paymentProofUploadRequest;
    private PaymentApprovedRequest paymentApprovedRequest;
    private OrderWarehouseResponse orderWarehouseResponse;

    private final UUID USER_ID = UUID.fromString("444e4567-e89b-12d3-a456-426614174003");
    private final UUID ORDER_ID = UUID.fromString("6d88c80e-6288-459a-9cc7-d03dd0145643");
    private final UUID ORDER_ID_FAILED = UUID.fromString("6d88c80e-6288-459a-9cc7-d03dd0145646");
    private final UUID PRODUCT_ID = UUID.fromString("111e4567-e89b-12d3-a456-426614174001");
    private final BigDecimal PRICE = new BigDecimal("20000");

    private final String AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0NDRlNDU2Ny1lODliLTEyZDMtYTQ1Ni00MjY2MTQxNzQwMDMiLCJlbWFpbCI6ImN1c3RvbWVyQGdtYWlsLmNvbSIsInJvbGUiOiJDVVNUT01FUiIsImlzX2VtYWlsX3ZlcmlmaWVkIjp0cnVlLCJ3YXJlaG91c2VfaWQiOm51bGwsImlhdCI6MTczNjIwMzc1OCwiZXhwIjoxNzY3NzM5NzU4fQ.1vFwvLFvAIfc_XH6zwRs6xHt8Zdkmjdr_hr9dSa4_Hk";
    private final String AUTHORIZATION_WAREHOUSE_ADMIN1 = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyMjJlNDU2Ny1lODliLTEyZDMtYTQ1Ni00MjY2MTQxNzQwMDEiLCJlbWFpbCI6IndhcmVob3VzZWFkbWluMUBnbWFpbC5jb20iLCJyb2xlIjoiV0FSRUhPVVNFX0FETUlOIiwiaXNfZW1haWxfdmVyaWZpZWQiOnRydWUsIndhcmVob3VzZV9pZCI6IjhiNjIyYWJlLWI0YzYtNDU0MC04NGRmLTdkYzBlOTc1MjUyOSIsImlhdCI6MTczNjI5MDYxNCwiZXhwIjoxNzY3ODI2NjE0fQ.-zhhRawQB6O5gjlEyEImMHbS8ATVvfB6hmPz3KrCqow";


    private Order order;
    private Order orderFailedPrice;

    @BeforeAll
    public void init() {
        System.out.println("init");
        authorizationHeader = AuthorizationHeader.builder()
                .authorization(AUTHORIZATION)
                .build();

        authorizationHeaderWarehouseAdmin1 = AuthorizationHeader.builder()
                .authorization(AUTHORIZATION_WAREHOUSE_ADMIN1)
                .build();

        createOrderCommand = CreateOrderCommand.builder()
                .userId(USER_ID)
                .cartId(null)
                .items(List.of(
                        OrderItem.builder()
                                .productId(UUID.fromString("111e4567-e89b-12d3-a456-426614174000"))
                                .quantity(1)
                                .price(new BigDecimal("15000"))
                                .subTotal(new BigDecimal("15000"))
                                .build(),
                        OrderItem.builder()
                                .productId(UUID.fromString("111e4567-e89b-12d3-a456-426614174001"))
                                .quantity(2)
                                .price(new BigDecimal("20000"))
                                .subTotal(new BigDecimal("40000"))
                                .build()))
                .shippingAddress(OrderAddress.builder()
                        .street("Swadaya Street II")
                        .postalCode("11410")
                        .city("West Jakarta")
                        .latitude("-6.1941932")
                        .longitude("-106.7989543")
                        .build())
                .totalPrice(new BigDecimal("55000"))
                .build();

        createOrderCommandFailedPrice = CreateOrderCommand.builder()
                .userId(USER_ID)
                .items(List.of(
                        OrderItem.builder()
                                .productId(UUID.fromString("111e4567-e89b-12d3-a456-426614174000"))
                                .quantity(1)
                                .price(new BigDecimal("15000"))
                                .subTotal(new BigDecimal("15000"))
                                .build(),
                        OrderItem.builder()
                                .productId(UUID.fromString("111e4567-e89b-12d3-a456-426614174001"))
                                .quantity(2)
                                .price(new BigDecimal("20000"))
                                .subTotal(new BigDecimal("40000"))
                                .build()))
                .shippingAddress(OrderAddress.builder()
                        .street("Swadaya Street II")
                        .postalCode("11410")
                        .city("West Jakarta")
                        .latitude("-6.1941932")
                        .longitude("-106.7989543")
                        .build())
                .totalPrice(new BigDecimal("0"))
                .build();

        paymentProofUploadRequest = PaymentProofUploadRequest.builder()
                .userId(USER_ID.toString())
                .orderId(ORDER_ID.toString())
                .price(new BigDecimal("55000"))
                .createdAt(java.time.Instant.now())
                .paymentStatus(PaymentStatus.UNDER_REVIEW)
                .build();

        paymentApprovedRequest = PaymentApprovedRequest.builder()
                .orderId(ORDER_ID.toString())
                .paymentId(UUID.randomUUID().toString())
                .paymentOrderStatus(PaymentStatus.PAID)
                .createdAt(java.time.Instant.now())
                .build();

        orderWarehouseResponse = OrderWarehouseResponse.builder()
                .orderId(ORDER_ID.toString())
                .warehouseId("8b622abe-b4c6-4540-84df-7dc0e9752529")
                .orderStatus(OrderStatus.PROCESSED)
                .build();

        createOrderIdCommand = CreateOrderIdCommand.builder()
                .orderId(ORDER_ID)
                .build();

        order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        orderFailedPrice = orderDataMapper.createOrderCommandToOrder(createOrderCommandFailedPrice);
        orderFailedPrice.setId(new OrderId(ORDER_ID_FAILED));

        Product product1 = Product.newBuilder()
                .withId(new ProductId(UUID.fromString("111e4567-e89b-12d3-a456-426614174000")))
                .withSku("SKU-001")
                .withPrice(new Money(new BigDecimal("15000")))
                .build();

        Product product2 = Product.newBuilder()
                .withId(new ProductId(PRODUCT_ID))
                .withSku("SKU-002")
                .withPrice(new Money(PRICE))
                .build();

        List<Warehouse> warehouses = getListWarehouse();

        Mockito.when(warehouseRepository.findAllWithProductsAndStocks()).thenReturn(warehouses);
        Mockito.when(productRepository.findById(new ProductId(UUID.fromString("111e4567-e89b-12d3-a456-426614174000")))).thenReturn(java.util.Optional.of(product1));
        Mockito.when(productRepository.findById(new ProductId(UUID.fromString("111e4567-e89b-12d3-a456-426614174001")))).thenReturn(java.util.Optional.of(product2));
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(orderFailedPrice);
    }

    @Test
    void contextLoads() {
        System.out.println("contextLoads");
        assertNotNull(orderApplicationService);
        assertNotNull(orderDataMapper);
        assertNotNull(warehouseRepository);
        assertNotNull(orderRepository);
        assertNotNull(productRepository);
    }

    @Test
    void testCreateOrder() {
        System.out.println("testCreateOrder");
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand, authorizationHeader);
        assertEquals(OrderStatus.AWAITING_PAYMENT.toString(), createOrderResponse.getOrderStatus(), "Order status should be AWAITING_PAYMENT after creation");
        assertEquals("Order created successfully!", createOrderResponse.getMessage(), "Order message should be 'Order created successfully' after creation");
        assertNotNull(createOrderResponse.getOrderId(), "Order ID should not be null after creation");
        System.out.println("Order ID: " + createOrderResponse.getOrderId());
        System.out.println("Order Status: " + createOrderResponse.getOrderStatus());
        System.out.println("Order Message: " + createOrderResponse.getMessage());
    }

    @Test
    void testCreateOrderFailedPrice() {
        System.out.println("testCreateOrderFailedPrice");
        OrderException thrownException = assertThrows(OrderException.class, () -> {
            orderApplicationService.createOrder(createOrderCommandFailedPrice, authorizationHeader);
        });

        assertEquals("Order total price is invalid", thrownException.getMessage(),
                "Exception message should be 'Order total price is invalid'");

        System.out.println("Exception Message: " + thrownException.getMessage());
    }

    /**
     * Pending order is customer has been uploaded the payment proof
     */
    @Test
    void testPendingOrder(CapturedOutput output) {
        System.out.println("testPendingOrder");

        Order order = Order.newBuilder()
                .withId(new OrderId(ORDER_ID))
                .withWarehouseId(new WarehouseId(UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529")))
                .withShippingMethod("JNE")
                .withUserId(new UserId(USER_ID))
                .withStatus(OrderStatus.AWAITING_PAYMENT)
                .build();

        Order orderMapper = orderDataMapper.paymentProofUploadedToOrder(paymentProofUploadRequest);

        Mockito.when(orderRepository.findById(new OrderId(ORDER_ID))).thenReturn(java.util.Optional.of(order));
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(orderMapper);

        paymentApplicationMessageListener.paymentProofUploaded(paymentProofUploadRequest);
        assertTrue(output.getOut().contains("Payment proof uploaded for order with id: " + ORDER_ID));
        assertTrue(output.getOut().contains("Change Status order to pending with id: " + ORDER_ID));
    }

    @Test
    void testPaidOrder(CapturedOutput output) {
        System.out.println("testPaidOrder");

        Order order = Order.newBuilder()
                .withId(new OrderId(ORDER_ID))
                .withWarehouseId(new WarehouseId(UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529")))
                .withShippingMethod("JNE")
                .withUserId(new UserId(USER_ID))
                .withStatus(OrderStatus.PENDING)
                .build();

        Order orderMapper = orderDataMapper.paymentApprovedToOrder(paymentApprovedRequest);

        Mockito.when(orderRepository.findById(new OrderId(ORDER_ID))).thenReturn(java.util.Optional.of(order));
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(orderMapper);

        paymentApplicationMessageListener.approveOrder(paymentApprovedRequest);
        assertTrue(output.getOut().contains("Payment approved for order with id: " + ORDER_ID));
        assertTrue(output.getOut().contains("Change Status order to paid with id: " + ORDER_ID));
    }

    @Test
    void testProcessedOrder() {
        System.out.println("testProcessedOrder");

        Order order = Order.newBuilder()
                .withId(new OrderId(ORDER_ID))
                .withWarehouseId(new WarehouseId(UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529")))
                .withShippingMethod("JNE")
                .withUserId(new UserId(USER_ID))
                .withStatus(OrderStatus.APPROVED)
                .build();

        Order orderMapper = orderDataMapper.orderWarehouseResponseToOrder(orderWarehouseResponse);

        Mockito.when(orderRepository.findById(new OrderId(ORDER_ID))).thenReturn(java.util.Optional.of(order));
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(orderMapper);

        orderWarehouseApplicationMessageListener.processedOrder(orderWarehouseResponse);
    }

    @Test
    void testShipOrder() {
        System.out.println("testShipOrder");

        Order order = Order.newBuilder()
                .withId(new OrderId(ORDER_ID))
                .withWarehouseId(new WarehouseId(UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529")))
                .withShippingMethod("JNE")
                .withUserId(new UserId(USER_ID))
                .withStatus(OrderStatus.PROCESSED)
                .build();

        Mockito.when(orderRepository.findById(new OrderId(ORDER_ID))).thenReturn(java.util.Optional.of(order));
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);

        CreateOrderResponse response = orderApplicationService.shipOrder(createOrderIdCommand, authorizationHeaderWarehouseAdmin1);

        assertEquals(OrderStatus.SHIPPED.toString(), response.getOrderStatus(), "Order status should be SHIPPED after shipping");
        assertEquals("Order shipped successfully!", response.getMessage(), "Order message should be 'Order shipped successfully' after shipping");

        System.out.println("Order ID: " + response.getOrderId());
        System.out.println("Order Status: " + response.getOrderStatus());
        System.out.println("Order Message: " + response.getMessage());
    }

    @Test
    void testConfirmOrder() {
        System.out.println("testConfirmOrder");

        Order order = Order.newBuilder()
                .withId(new OrderId(ORDER_ID))
                .withWarehouseId(new WarehouseId(UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529")))
                .withShippingMethod("JNE")
                .withUserId(new UserId(USER_ID))
                .withStatus(OrderStatus.SHIPPED)
                .build();

        Mockito.when(orderRepository.findById(new OrderId(ORDER_ID))).thenReturn(java.util.Optional.of(order));
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);

        CreateOrderResponse response = orderApplicationService.confirmOrder(createOrderIdCommand, authorizationHeader);
        assertEquals(OrderStatus.CONFIRMED.toString(), response.getOrderStatus(), "Order status should be CONFIRMED after confirmation");
        assertEquals("Order confirmed successfully!", response.getMessage(), "Order message should be 'Order confirmed successfully' after confirmation");
        System.out.println("Order ID: " + response.getOrderId());
        System.out.println("Order Status: " + response.getOrderStatus());
        System.out.println("Order Message: " + response.getMessage());
    }

    List<Warehouse> getListWarehouse() {
        return List.of(
                Warehouse.builder()
                        .withId(new WarehouseId(UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529")))
                        .withName("Indomarco 1")
                        .withAddress(new Address(
                                "West Ancol Street",
                                "14430",
                                "North Jakarta",
                                "-6.1219211",
                                "106.8147549"
                        ))
                        .withStocks(List.of(
                                Stock.builder()
                                        .withId(new StockId(UUID.fromString("111e4567-e89b-12d3-a456-426614174002")))
                                        .withWarehouseId(new WarehouseId(UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529")))
                                        .withProductId(new ProductId(UUID.fromString("111e4567-e89b-12d3-a456-426614174000")))
//                                        .withProducts(List.of(
//                                                        Product.newBuilder()
//                                                                .withId(new ProductId(UUID.fromString("111e4567-e89b-12d3-a456-426614174000")))
//                                                                .withSku("SKU-001")
//                                                                .withPrice(new Money(new BigDecimal("15000")))
//                                                                .build()
//                                                )
//                                        )
                                        .withQuantity(100)
                                        .build(),
                                Stock.builder()
                                        .withId(new StockId(UUID.fromString("111e4567-e89b-12d3-a456-426614174003")))
                                        .withWarehouseId(new WarehouseId(UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529")))
                                        .withProductId(new ProductId(UUID.fromString("111e4567-e89b-12d3-a456-426614174001")))
//                                        .withProducts(List.of(
//                                                        Product.newBuilder()
//                                                                .withId(new ProductId(UUID.fromString("111e4567-e89b-12d3-a456-426614174001")))
//                                                                .withSku("SKU-002")
//                                                                .withPrice(new Money(new BigDecimal("20000")))
//                                                                .build()
//
//                                                )
//                                        )
                                        .withQuantity(100)
                                        .build()
                        ))
                        .build(),

                Warehouse.builder()
                        .withId(new WarehouseId(UUID.fromString("a04adf69-11a5-440f-a6b9-56d9a718e9a7")))
                        .withName("Indomarco 2")
                        .withAddress(new Address(
                                "Ancol Street",
                                "14430",
                                "North Jakarta",
                                "-6.1213429",
                                "106.8141887"
                        ))
                        .withStocks(List.of(
                                Stock.builder()
                                        .withId(new StockId(UUID.fromString("111e4567-e89b-12d3-a456-426614174004")))
                                        .withWarehouseId(new WarehouseId(UUID.fromString("a04adf69-11a5-440f-a6b9-56d9a718e9a7")))
                                        .withProductId(new ProductId(UUID.fromString("111e4567-e89b-12d3-a456-426614174001")))
//                                        .withProducts(List.of(
//                                                Product.newBuilder()
//                                                        .withId(new ProductId(UUID.fromString("111e4567-e89b-12d3-a456-426614174001")))
//                                                        .withSku("SKU-002")
//                                                        .withPrice(new Money(new BigDecimal("20000")))
//                                                        .build()
//                                        ))
                                        .withQuantity(100)
                                        .build()
                        ))
                        .build()
        );
    }
}
