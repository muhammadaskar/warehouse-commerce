package com.ecommerce.app.product.container;

import com.ecommerce.app.product.domain.core.ProductDomainService;
import com.ecommerce.app.product.domain.core.ProductDomainServiceImpl;
import com.ecommerce.app.product.domain.core.UserDomainService;
import com.ecommerce.app.product.domain.core.UserDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ProductDomainService productDomainService() {
        return new ProductDomainServiceImpl();
    }

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }
}
