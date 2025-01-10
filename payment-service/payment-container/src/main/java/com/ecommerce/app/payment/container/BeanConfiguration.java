package com.ecommerce.app.payment.container;

import com.ecommerce.app.payment.domain.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImpl();
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
    
    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }
}
