package com.ecommerce.app.order.application.service;

import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.order.application.service.ports.output.*;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderConfirmedRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderProcessedRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderShippedRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderPaidEventRequestMessagePublisher;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderPaymentProofUploadedResponseMessagePublisher;
import com.ecommerce.app.order.domain.core.OrderDomainService;
import com.ecommerce.app.order.domain.core.OrderDomainServiceImpl;
import com.ecommerce.app.order.domain.core.UserDomainService;
import com.ecommerce.app.order.domain.core.UserDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages =  "com.ecommerce.app.order.application.service")
public class OrderTestConfiguration {

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }

    @Bean
    public WarehouseRepository warehouseRepository() {
        return Mockito.mock(WarehouseRepository.class);
    }

    @Bean
    public StockRepository stockRepository() {
        return Mockito.mock(StockRepository.class);
    }

    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderPaymentProofUploadedResponseMessagePublisher orderPaymentProofUploadedResponseMessagePublisher() {
        return Mockito.mock(OrderPaymentProofUploadedResponseMessagePublisher.class);
    }

    @Bean
    public OrderPaidEventRequestMessagePublisher orderPaidEventRequestMessagePublisher() {
        return Mockito.mock(OrderPaidEventRequestMessagePublisher.class);
    }

    @Bean
    public OrderProcessedRequestMessagePublisher orderProcessedRequestMessagePublisher() {
        return Mockito.mock(OrderProcessedRequestMessagePublisher.class);
    }

    @Bean
    public OrderShippedRequestMessagePublisher orderShippedRequestMessagePublisher() {
        return Mockito.mock(OrderShippedRequestMessagePublisher.class);
    }

    @Bean
    public OrderConfirmedRequestMessagePublisher orderConfirmedRequestMessageListener() {
        return Mockito.mock(OrderConfirmedRequestMessagePublisher.class);
    }

    @Bean
    public GlobalVerifyJWT globalVerifyJWT() {
        return new GlobalVerifyJWT();
    }
}
