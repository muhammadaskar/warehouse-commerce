package com.ecommerce.app.warehouse.application.rest;

import com.ecommerce.app.common.application.security.annotation.RequiresRole;
import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.service.dto.create.*;
import com.ecommerce.app.warehouse.domain.service.ports.input.service.WarehouseApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequiresRole(UserRole.SUPER_ADMIN)
    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses() {
        List<WarehouseResponse> warehouses = warehouseApplicationService.getAllWarehouses();
        return ResponseEntity.ok(warehouses);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @GetMapping(value = "{warehouseId}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable("warehouseId") UUID warehouseId) {
        WarehouseResponse warehouse = warehouseApplicationService.getWarehouseBydId(WarehouseIdQuery.
                builder().warehouseId(warehouseId).build());
        return ResponseEntity.ok(warehouse);
    }

    @RequiresRole(UserRole.SUPER_ADMIN)
    @PostMapping
    public ResponseEntity<CreateWarehouseResponse> createWarehouse(@RequestBody CreateWarehouseCommand createWarehouseCommand) {
        CreateWarehouseResponse createWarehouseResponse = warehouseApplicationService.createWarehouse(createWarehouseCommand);
        return ResponseEntity.ok(createWarehouseResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @GetMapping("/stock/{stockId}")
    public ResponseEntity<StockResponse> getStockById(@PathVariable("stockId") UUID stockId,
                                                      @RequestHeader("Authorization") AuthorizationHeader authorizationHeader) {
        StockResponse stock = warehouseApplicationService.findStockById(StockIdQuery.builder().stockId(stockId).build(), authorizationHeader);
        return ResponseEntity.ok(stock);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @PutMapping("/stock")
    public ResponseEntity<StockRequestedResponse> updateStock(@RequestBody StockRequestCommand stockRequestCommand,
                                                              @RequestHeader("Authorization") AuthorizationHeader authorizationHeader) {
        StockRequestedResponse stockRequestedResponse = warehouseApplicationService.updateStock(stockRequestCommand, authorizationHeader);
        return ResponseEntity.ok(stockRequestedResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @PostMapping("/stock")
    public ResponseEntity<StockRequestedResponse> createStock(@RequestBody StockRequestCommand stockRequestCommand,
                                                              @RequestHeader("Authorization") AuthorizationHeader authorizationHeader) {
        StockRequestedResponse stockRequestedResponse = warehouseApplicationService.createStock(stockRequestCommand, authorizationHeader);
        return ResponseEntity.ok(stockRequestedResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @GetMapping("/stock-journals/{productId}/{warehouseId}")
    public ResponseEntity<List<StockJournalResponse>> getStockJournals(@PathVariable("productId") UUID productId,
                                                                       @PathVariable("warehouseId") UUID warehouseId,
                                                                       @RequestHeader("Authorization") AuthorizationHeader authorizationHeader) {
        List<StockJournalResponse> stockJournals = warehouseApplicationService.getStockJournalsByWarehouseId(
                ProductIdQuery.builder().productId(productId).build(),
                WarehouseIdQuery.builder().warehouseId(warehouseId).build(), authorizationHeader);
        return ResponseEntity.ok(stockJournals);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @GetMapping("/stock-transfers/by-source-warehouse/{warehouseId}")
    public ResponseEntity<List<StockTransferResponse>> getStockTransfers(@PathVariable("warehouseId") UUID warehouseId,
                                                                         @RequestHeader("Authorization") AuthorizationHeader authorizationHeader) {
        List<StockTransferResponse> stockTransfers = warehouseApplicationService.findAllStockTransferBySourceWarehouseId(
                WarehouseIdQuery.builder().warehouseId(warehouseId).build(), authorizationHeader);
        return ResponseEntity.ok(stockTransfers);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @GetMapping("/stock-transfers/by-destination-warehouse/{warehouseId}")
    public ResponseEntity<List<StockTransferResponse>> getStockTransfersByDestinationWarehouseId(@PathVariable("warehouseId") UUID warehouseId,
                                                                                                 @RequestHeader("Authorization") AuthorizationHeader authorizationHeader) {
        List<StockTransferResponse> stockTransfers = warehouseApplicationService.findAllStockTransferByDestinationWarehouseId(
                WarehouseIdQuery.builder().warehouseId(warehouseId).build(), authorizationHeader);
        return ResponseEntity.ok(stockTransfers);
    }
}
