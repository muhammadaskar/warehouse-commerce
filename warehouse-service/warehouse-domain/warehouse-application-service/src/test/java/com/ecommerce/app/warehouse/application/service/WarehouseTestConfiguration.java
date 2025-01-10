package com.ecommerce.app.warehouse.application.service;

import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.warehouse.domain.core.UserDomainService;
import com.ecommerce.app.warehouse.domain.core.UserDomainServiceImpl;
import com.ecommerce.app.warehouse.domain.core.WarehouseDomainService;
import com.ecommerce.app.warehouse.domain.core.WarehouseDomainServiceImpl;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.order.OrderShippedResponseMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.order.OrderWarehouseResponseMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.*;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.warehouse.WarehouseCreatedRequestMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.*;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.ecommerce.app.warehouse.domain.service")
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
    public OrderWarehouseResponseMessagePublisher orderWarehouseResponseMessagePublisher() {
        return Mockito.mock(OrderWarehouseResponseMessagePublisher.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
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
    public StockTransferRepository stockTransferRepository() {
        return Mockito.mock(StockTransferRepository.class);
    }

    @Bean
    public ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }

    @Bean
    public WarehouseDomainService warehouseDomainService() {
        return new WarehouseDomainServiceImpl();
    }

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }

    @Bean
    public StockJournalRepository stockJournalRepository() {
        return Mockito.mock(StockJournalRepository.class);
    }

    @Bean
    public StockTransferredUpdateMessagePublisher stockUpdateMessagePublisher() {
        return Mockito.mock(StockTransferredUpdateMessagePublisher.class);
    }

    @Bean
    public OrderShippedResponseMessagePublisher orderShippedResponseMessagePublisher() {
        return Mockito.mock(OrderShippedResponseMessagePublisher.class);
    }

    @Bean
    public StockShippedUpdateMessagePublisher stockShippedUpdateMessagePublisher() {
        return Mockito.mock(StockShippedUpdateMessagePublisher.class);
    }

    @Bean
    public StockUpdatedMessagePublisher stockUpdatedMessagePublisher() {
        return Mockito.mock(StockUpdatedMessagePublisher.class);
    }

    @Bean
    public StockCreatedMessagePublisher stockCreatedMessagePublisher() {
        return Mockito.mock(StockCreatedMessagePublisher.class);
    }

    @Bean
    public GlobalVerifyJWT globalVerifyJWT() {
        return new GlobalVerifyJWT();
    }
}
