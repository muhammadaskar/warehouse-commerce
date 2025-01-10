package com.ecommerce.app.order.container;

import com.ecommerce.app.order.domain.core.OrderDomainService;
import com.ecommerce.app.order.domain.core.OrderDomainServiceImpl;
import com.ecommerce.app.order.domain.core.UserDomainService;
import com.ecommerce.app.order.domain.core.UserDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }
}
