package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.ports.output.repository.UserRepository;
import com.ecommerce.app.user.application.service.ports.output.repository.WarehouseRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages =  "com.ecommerce.app")
public class UserTestConfiguration {
    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public WarehouseRepository warehouseRepository() {
        return Mockito.mock(WarehouseRepository.class);
    }
}
