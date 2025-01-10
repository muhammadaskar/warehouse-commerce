package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.payment.application.service.ports.output.OrderRepository;
import com.ecommerce.app.payment.application.service.ports.output.PaymentRepository;
import com.ecommerce.app.payment.application.service.ports.output.UserRepository;
import com.ecommerce.app.payment.application.service.ports.output.message.publisher.payment.PaymentApprovedMessagePublisher;
import com.ecommerce.app.payment.application.service.ports.output.message.publisher.payment.PaymentProofUploadedMessagePublisher;
import com.ecommerce.app.payment.domain.core.*;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages =  "com.ecommerce.app.payment.application.service")
public class PaymentTestConfiguration {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImpl();
    }

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

    @Bean
    public PaymentRepository paymentRepository() {
        return Mockito.mock(PaymentRepository.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public PaymentProofUploadedMessagePublisher paymentProofUploadedMessagePublisher() {
        return Mockito.mock(PaymentProofUploadedMessagePublisher.class);
    }

    @Bean
    public PaymentApprovedMessagePublisher paymentApprovedMessagePublisher() {
        return Mockito.mock(PaymentApprovedMessagePublisher.class);
    }

    @Bean
    public GlobalVerifyJWT globalVerifyJWT() {
        return new GlobalVerifyJWT();
    }

}
