package com.ecommerce.app.product.application.service;

import com.ecommerce.app.product.application.service.ports.output.ProductRepository;
import com.ecommerce.app.product.application.service.ports.output.UserRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages =  "com.ecommerce.app")
public class ProductTestConfiguration {

    @Bean
    public ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }
}
