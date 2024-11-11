package com.ecommerce.app.warehouse.application.rest;

import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferStatus;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseResponse;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockTransferCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockTransferResponse;
import com.ecommerce.app.warehouse.domain.service.ports.input.service.WarehouseApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/warehouses", produces = "application/vnd.api.v1+json")
public class WarehouseController {

    private final WarehouseApplicationService warehouseApplicationService;

    public WarehouseController(WarehouseApplicationService warehouseApplicationService) {
        this.warehouseApplicationService = warehouseApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateWarehouseResponse> createWarehouse(@RequestBody CreateWarehouseCommand createWarehouseCommand) {
        CreateWarehouseResponse createWarehouseResponse = warehouseApplicationService.createWarehouse(createWarehouseCommand);
        return ResponseEntity.ok(createWarehouseResponse);
    }

    @PostMapping(value = "/transfer-stock")
    public ResponseEntity<StockTransferResponse> transferStock(@RequestBody StockTransferCommand stockTransferCommand) {
        log.debug("transferStock!!!");
        StockTransferResponse stockTransferResponse = warehouseApplicationService.transferStock(stockTransferCommand);
        return ResponseEntity.ok(stockTransferResponse);
    }

    @GetMapping
    public ResponseEntity<List> getList() {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StockTransferResponse dummyResponse = new StockTransferResponse(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    StockTransferStatus.TRANSFER,
                    "Successfully transfer"
            );

            list.add(dummyResponse);
        }
        log.info("getList!!!");

        return ResponseEntity.ok(list);
    }
}
