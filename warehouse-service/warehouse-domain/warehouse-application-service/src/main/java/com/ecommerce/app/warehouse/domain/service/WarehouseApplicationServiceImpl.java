package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;
import com.ecommerce.app.warehouse.domain.core.entity.StockTransfer;
import com.ecommerce.app.warehouse.domain.service.dto.create.*;
import com.ecommerce.app.warehouse.domain.service.ports.input.service.WarehouseApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Validated
@Service
public class WarehouseApplicationServiceImpl implements WarehouseApplicationService {

    private final WarehouseHandler warehouseHandler;
    private final WarehouseStockHandler warehouseStockHandler;

    public WarehouseApplicationServiceImpl(WarehouseHandler warehouseHandler, WarehouseStockHandler warehouseStockHandler) {
        this.warehouseHandler = warehouseHandler;
        this.warehouseStockHandler = warehouseStockHandler;
    }

    @Override
    public WarehouseResponse getWarehouseBydId(WarehouseIdQuery warehouseIdQuery) {
        return warehouseHandler.getWarehouseById(warehouseIdQuery);
    }

    @Override
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseHandler.getAllWarehouses();
    }

    @Override
    public CreateWarehouseResponse createWarehouse(CreateWarehouseCommand command) {
        return warehouseHandler.createWarehouse(command);
    }

    @Override
    public StockResponse findStockById(StockIdQuery stockIdQuery, AuthorizationHeader authorizationHeader) {
        return warehouseStockHandler.findStockById(stockIdQuery, authorizationHeader);
    }

    @Override
    public StockRequestedResponse updateStock(StockRequestCommand stockRequestCommand, AuthorizationHeader authorizationHeader) {
        return warehouseStockHandler.updateStock(stockRequestCommand, authorizationHeader);
    }

    @Override
    public StockRequestedResponse createStock(StockRequestCommand stockRequestCommand, AuthorizationHeader authorizationHeader) {
        return warehouseStockHandler.createStock(stockRequestCommand, authorizationHeader);
    }

    @Override
    public List<StockJournalResponse> getStockJournalsByWarehouseId(ProductIdQuery productIdQuery, WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader) {
        return warehouseStockHandler.findStockJournalsByWarehouseId(productIdQuery, warehouseIdQuery, authorizationHeader);
    }

    @Override
    public List<StockTransferResponse> findAllStockTransferBySourceWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader) {
        return warehouseStockHandler.findAllStockTransferBySourceWarehouseId(warehouseIdQuery, authorizationHeader);
    }

    @Override
    public List<StockTransferResponse> findAllStockTransferByDestinationWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader) {
        return warehouseStockHandler.findAllStockTransferByDestinationWarehouseId(warehouseIdQuery, authorizationHeader);
    }
}
