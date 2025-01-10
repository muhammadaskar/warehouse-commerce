package com.ecommerce.app.user.application.service;

import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.user.application.service.ports.output.message.publisher.user.UserCreatedMessagePublisher;
import com.ecommerce.app.user.application.service.ports.output.message.publisher.user.WarehouseAdminCreatedMessagePublisher;
import com.ecommerce.app.user.application.service.ports.output.repository.UserAddressRepository;
import com.ecommerce.app.user.application.service.ports.output.repository.UserRepository;
import com.ecommerce.app.user.application.service.ports.output.repository.WarehouseRepository;
import com.ecommerce.app.user.domain.core.UserAddressDomainService;
import com.ecommerce.app.user.domain.core.UserAddressDomainServiceImpl;
import com.ecommerce.app.user.domain.core.UserDomainService;
import com.ecommerce.app.user.domain.core.UserDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages =  "com.ecommerce.app.user.application.service")
public class UserTestConfiguration {
    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }

    @Bean
    public UserAddressDomainService userAddressDomainService() {
        return new UserAddressDomainServiceImpl();
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public UserAddressRepository userAddressRepository() {
        return Mockito.mock(UserAddressRepository.class);
    }

    @Bean
    public WarehouseRepository warehouseRepository() {
        return Mockito.mock(WarehouseRepository.class);
    }

    @Bean
    public UserCreatedMessagePublisher userCreatedMessagePublisher() {
        return Mockito.mock(UserCreatedMessagePublisher.class);
    }

    @Bean
    public WarehouseAdminCreatedMessagePublisher warehouseAdminCreatedMessagePublisher() {
        return Mockito.mock(WarehouseAdminCreatedMessagePublisher.class);
    }

    public GlobalVerifyJWT globalVerifyJWT() {
        return new GlobalVerifyJWT();
    }

}
