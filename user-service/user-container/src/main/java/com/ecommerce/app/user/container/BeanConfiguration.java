package com.ecommerce.app.user.container;

import com.ecommerce.app.user.domain.core.UserDomainService;
import com.ecommerce.app.user.domain.core.UserDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }
}
