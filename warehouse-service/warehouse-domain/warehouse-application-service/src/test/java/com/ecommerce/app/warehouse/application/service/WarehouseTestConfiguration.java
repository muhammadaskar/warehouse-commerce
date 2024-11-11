package com.ecommerce.app.warehouse.application.service;

import com.ecommerce.app.warehouse.domain.core.WarehouseDomainService;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.WarehouseStockTransferedMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.warehouse.WarehouseCreatedRequestMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseRepository;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseStockRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.ecommerce.app")
public class WarehouseTestConfiguration {
    @Bean
    public WarehouseCreatedRequestMessagePublisher warehouseCreatedRequestMessagePublisher() {
        return Mockito.mock(WarehouseCreatedRequestMessagePublisher.class);
    }

    @Bean
    public WarehouseStockTransferedMessagePublisher warehouseStockTransferedMessagePublisher() {
        return Mockito.mock(WarehouseStockTransferedMessagePublisher.class);
    }

    @Bean
    public WarehouseRepository warehouseRepository() {
        return Mockito.mock(WarehouseRepository.class);
    }

    @Bean
    public WarehouseStockRepository warehouseStockRepository() {
        return Mockito.mock(WarehouseStockRepository.class);
    }

    @Bean
    public WarehouseDomainService warehouseDomainService() {
        return Mockito.mock(WarehouseDomainService.class);
    }
}
