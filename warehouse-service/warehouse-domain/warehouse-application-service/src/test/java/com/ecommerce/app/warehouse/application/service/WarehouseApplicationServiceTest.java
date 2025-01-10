package com.ecommerce.app.warehouse.application.service;

import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.entity.Product;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.exception.ProductNotFoundException;
import com.ecommerce.app.warehouse.domain.core.exception.UserForbidden;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockId;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockJournalId;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseResponse;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockRequestCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockRequestedResponse;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.input.service.WarehouseApplicationService;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.ProductRepository;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.StockJournalRepository;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseRepository;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseStockRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {WarehouseTestConfiguration.class})
@ExtendWith(OutputCaptureExtension.class)
public class WarehouseApplicationServiceTest {

    @Autowired
    private WarehouseApplicationService warehouseApplicationService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseStockRepository warehouseStockRepository;

    @Autowired
    private StockJournalRepository stockJournalRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseDataMapper warehouseDataMapper;

    private AuthorizationHeader authorizationHeaderWarehouseAdmin1;
    private AuthorizationHeader authorizationHeaderWarehouseAdmin2;

    private CreateWarehouseCommand createWarehouseCommand;
    private StockRequestCommand stockRequestCommand;

    private final UUID PRODUCT_ID = UUID.fromString("111e4567-e89b-12d3-a456-426614174000");
    private final UUID WAREHOUSE_ID = UUID.fromString("8b622abe-b4c6-4540-84df-7dc0e9752529");

    private final String AUTHORIZATION_WAREHOUSE_ADMIN1 = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyMjJlNDU2Ny1lODliLTEyZDMtYTQ1Ni00MjY2MTQxNzQwMDEiLCJlbWFpbCI6IndhcmVob3VzZWFkbWluMUBnbWFpbC5jb20iLCJyb2xlIjoiV0FSRUhPVVNFX0FETUlOIiwiaXNfZW1haWxfdmVyaWZpZWQiOnRydWUsIndhcmVob3VzZV9pZCI6IjhiNjIyYWJlLWI0YzYtNDU0MC04NGRmLTdkYzBlOTc1MjUyOSIsImlhdCI6MTczNjI5MDYxNCwiZXhwIjoxNzY3ODI2NjE0fQ.-zhhRawQB6O5gjlEyEImMHbS8ATVvfB6hmPz3KrCqow";
    private final String AUTHORIZATION_WAREHOUSE_ADMIN2 = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzMzNlNDU2Ny1lODliLTEyZDMtYTQ1Ni00MjY2MTQxNzQwMDIiLCJlbWFpbCI6IndhcmVob3VzZWFkbWluMkBnbWFpbC5jb20iLCJyb2xlIjoiV0FSRUhPVVNFX0FETUlOIiwiaXNfZW1haWxfdmVyaWZpZWQiOnRydWUsIndhcmVob3VzZV9pZCI6ImEwNGFkZjY5LTExYTUtNDQwZi1hNmI5LTU2ZDlhNzE4ZTlhNyIsImlhdCI6MTczNjI5NjM2NiwiZXhwIjoxNzY3ODMyMzY2fQ.OzPKhBzi9Ta0IMzE2YSvUzvNqqn4rZj7HylV_xFr4Kg";

    @BeforeAll
    public void init() {
        authorizationHeaderWarehouseAdmin1 = AuthorizationHeader.builder()
                .authorization(AUTHORIZATION_WAREHOUSE_ADMIN1)
                .build();

        authorizationHeaderWarehouseAdmin2 = AuthorizationHeader.builder()
                .authorization(AUTHORIZATION_WAREHOUSE_ADMIN2)
                .build();

        createWarehouseCommand = CreateWarehouseCommand.builder()
                .name("Warehouse 1")
                .street("Jl Karbela 1")
                .postalCode("11410")
                .city("Jakarta")
                .latitude("-6.21462")
                .longitude("106.84513")
                .build();

        stockRequestCommand = StockRequestCommand.builder()
                .productId(PRODUCT_ID)
                .warehouseId(WAREHOUSE_ID)
                .quantity(10)
                .build();

        Warehouse warehouse = warehouseDataMapper.warehouseCommandToWarehouse(createWarehouseCommand);
        warehouse.setId(new WarehouseId(WAREHOUSE_ID));
        Mockito.when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);
    }

    @Test
    void testCreateWarehouse() {
        CreateWarehouseResponse createWarehouseResponse = warehouseApplicationService.createWarehouse(createWarehouseCommand);
        assertNotNull(createWarehouseResponse);
        assertNotNull(createWarehouseResponse.getWarehouseId());
        assertEquals("warehouse created successfully", createWarehouseResponse.getMessage());
        System.out.println("Warehouse ID: " + createWarehouseResponse.getWarehouseId());
        System.out.println("Message: " + createWarehouseResponse.getMessage());
    }

    @Test
    void testCreateStock() {
        Warehouse warehouse = Warehouse.builder()
                .withId(new WarehouseId(WAREHOUSE_ID))
                .build();

        Product product = Product.newBuilder()
                .withId(new ProductId(PRODUCT_ID))
                .build();

        Stock stock = warehouseDataMapper.stockRequestCommandToStock(stockRequestCommand);
        stock.setId(new StockId(UUID.randomUUID()));
        StockJournal stockJournal = warehouseDataMapper.stockToStockJournal(stock);
        stockJournal.setId(new StockJournalId(UUID.randomUUID()));

        Mockito.when(warehouseStockRepository.findByProductIdAndWarehouseId(new ProductId(PRODUCT_ID), new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.empty());
        Mockito.when(warehouseRepository.findById(new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.of(warehouse));
        Mockito.when(productRepository.findById(new ProductId(PRODUCT_ID)))
                .thenReturn(Optional.of(product));
        Mockito.when(warehouseStockRepository.save(any(Stock.class)))
                .thenReturn(stock);
        Mockito.when(stockJournalRepository.save(any(StockJournal.class)))
                .thenReturn(stockJournal);

        StockRequestedResponse response =  warehouseApplicationService.createStock(stockRequestCommand, authorizationHeaderWarehouseAdmin1);
        assertEquals("Stock created successfully", response.getMessage(), "Message should be stock created successfully");
        System.out.println("Message: " + response.getMessage());
    }

    @Test
    void testUpdateStock() {
        Warehouse warehouse = Warehouse.builder()
                .withId(new WarehouseId(WAREHOUSE_ID))
                .build();

        Product product = Product.newBuilder()
                .withId(new ProductId(PRODUCT_ID))
                .build();

        Stock stock = warehouseDataMapper.stockRequestCommandToStock(stockRequestCommand);
        stock.setId(new StockId(UUID.randomUUID()));
        StockJournal stockJournal = warehouseDataMapper.stockToStockJournal(stock);
        stockJournal.setId(new StockJournalId(UUID.randomUUID()));

        Mockito.when(warehouseStockRepository.findByProductIdAndWarehouseId(new ProductId(PRODUCT_ID), new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.of(stock));
        Mockito.when(warehouseRepository.findById(new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.of(warehouse));
        Mockito.when(productRepository.findById(new ProductId(PRODUCT_ID)))
                .thenReturn(Optional.of(product));
        Mockito.when(warehouseStockRepository.save(any(Stock.class)))
                .thenReturn(stock);
        Mockito.when(stockJournalRepository.save(any(StockJournal.class)))
                .thenReturn(stockJournal);

        StockRequestedResponse response =  warehouseApplicationService.updateStock(stockRequestCommand, authorizationHeaderWarehouseAdmin1);
        assertEquals("Stock updated successfully", response.getMessage(), "Message should be stock updated successfully");
        System.out.println("Message: " + response.getMessage());
    }

    @Test
    void testCreateStockWithWrongWarehouseAdmin() {
        Warehouse warehouse = Warehouse.builder()
                .withId(new WarehouseId(WAREHOUSE_ID))
                .build();

        Product product = Product.newBuilder()
                .withId(new ProductId(PRODUCT_ID))
                .build();

        Stock stock = warehouseDataMapper.stockRequestCommandToStock(stockRequestCommand);
        stock.setId(new StockId(UUID.randomUUID()));
        StockJournal stockJournal = warehouseDataMapper.stockToStockJournal(stock);
        stockJournal.setId(new StockJournalId(UUID.randomUUID()));

        Mockito.when(warehouseStockRepository.findByProductIdAndWarehouseId(new ProductId(PRODUCT_ID), new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.empty());
        Mockito.when(warehouseRepository.findById(new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.of(warehouse));
        Mockito.when(productRepository.findById(new ProductId(PRODUCT_ID)))
                .thenReturn(Optional.of(product));
        Mockito.when(warehouseStockRepository.save(any(Stock.class)))
                .thenReturn(stock);
        Mockito.when(stockJournalRepository.save(any(StockJournal.class)))
                .thenReturn(stockJournal);

        assertThrows(UserForbidden.class, () -> {
            warehouseApplicationService.createStock(stockRequestCommand, authorizationHeaderWarehouseAdmin2);
        });
    }

    @Test
    void testUpdateStockWithWrongWarehouseAdmin() {
        Warehouse warehouse = Warehouse.builder()
                .withId(new WarehouseId(WAREHOUSE_ID))
                .build();

        Product product = Product.newBuilder()
                .withId(new ProductId(PRODUCT_ID))
                .build();

        Stock stock = warehouseDataMapper.stockRequestCommandToStock(stockRequestCommand);
        stock.setId(new StockId(UUID.randomUUID()));
        StockJournal stockJournal = warehouseDataMapper.stockToStockJournal(stock);
        stockJournal.setId(new StockJournalId(UUID.randomUUID()));

        Mockito.when(warehouseStockRepository.findByProductIdAndWarehouseId(new ProductId(PRODUCT_ID), new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.of(stock));
        Mockito.when(warehouseRepository.findById(new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.of(warehouse));
        Mockito.when(productRepository.findById(new ProductId(PRODUCT_ID)))
                .thenReturn(Optional.of(product));
        Mockito.when(warehouseStockRepository.save(any(Stock.class)))
                .thenReturn(stock);
        Mockito.when(stockJournalRepository.save(any(StockJournal.class)))
                .thenReturn(stockJournal);

        assertThrows(UserForbidden.class, () -> {
            warehouseApplicationService.updateStock(stockRequestCommand, authorizationHeaderWarehouseAdmin2);
        });
    }

    @Test
    void testCreateStockWithProductNotFound(CapturedOutput output) {
        Warehouse warehouse = Warehouse.builder()
                .withId(new WarehouseId(WAREHOUSE_ID))
                .build();

        Stock stock = warehouseDataMapper.stockRequestCommandToStock(stockRequestCommand);
        stock.setId(new StockId(UUID.randomUUID()));
        StockJournal stockJournal = warehouseDataMapper.stockToStockJournal(stock);
        stockJournal.setId(new StockJournalId(UUID.randomUUID()));

        Mockito.when(warehouseStockRepository.findByProductIdAndWarehouseId(new ProductId(PRODUCT_ID), new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.empty());
        Mockito.when(warehouseRepository.findById(new WarehouseId(WAREHOUSE_ID)))
                .thenReturn(Optional.of(warehouse));
        Mockito.when(productRepository.findById(new ProductId(PRODUCT_ID)))
                .thenReturn(Optional.empty());
        Mockito.when(warehouseStockRepository.save(any(Stock.class)))
                .thenReturn(stock);
        Mockito.when(stockJournalRepository.save(any(StockJournal.class)))
                .thenReturn(stockJournal);

        assertThrows(ProductNotFoundException.class, () -> {
            warehouseApplicationService.createStock(stockRequestCommand, authorizationHeaderWarehouseAdmin1);
        });
        assertTrue(output.toString().contains("Product with id " + PRODUCT_ID + " not found"));
    }
}
