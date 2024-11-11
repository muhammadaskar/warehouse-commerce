package com.ecommerce.app.warehouse.domain.container;

import com.ecommerce.app.warehouse.domain.core.WarehouseDomainService;
import com.ecommerce.app.warehouse.domain.core.WarehouseDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public WarehouseDomainService warehouseDomainService() {
        return new WarehouseDomainServiceImpl();
    }
}
