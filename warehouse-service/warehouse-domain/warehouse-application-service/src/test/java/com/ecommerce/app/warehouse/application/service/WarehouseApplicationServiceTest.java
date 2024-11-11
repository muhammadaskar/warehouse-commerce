package com.ecommerce.app.warehouse.application.service;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseCommand;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.input.service.WarehouseApplicationService;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseRepository;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseStockRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = WarehouseTestConfiguration.class)
public class WarehouseApplicationServiceTest {

    @Autowired
    private WarehouseApplicationService warehouseApplicationService;

    @Autowired
    private WarehouseDataMapper warehouseDataMapper;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseStockRepository warehouseStockRepository;

    private CreateWarehouseCommand createWarehouseCommand;

    private final UUID WAREHOUSE_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb");

    @BeforeAll
    public void init() {
        createWarehouseCommand = CreateWarehouseCommand.builder()
                .name("Warehouse 1")
                .street("Jl Karbela 1")
                .postalCode("11410")
                .city("Jakarta")
                .build();


        Warehouse warehouse = warehouseDataMapper.warehouseCommandToWarehouse(createWarehouseCommand);
        WarehouseId warehouseId = new WarehouseId(WAREHOUSE_ID);
        warehouse.setId(warehouseId);
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);
    }

    @Test
    void testCreateWarehouse() {
        System.out.println(createWarehouseCommand.getName());
        System.out.println(createWarehouseCommand.getStreet());
        System.out.println(createWarehouseCommand.getPostalCode());
        System.out.println(createWarehouseCommand.getCity());
//        CreateWarehouseResponse createWarehouseResponse = warehouseApplicationService.createWarehouse(createWarehouseCommand);
//        System.out.println("Warehouse ID = " + createWarehouseResponse.getWarehouseId());
//        assertEquals(createWarehouseResponse.getWarehouseName(), "Warehouse 1");
//        assertNotNull(createWarehouseResponse.getWarehouseId());
    }
}
