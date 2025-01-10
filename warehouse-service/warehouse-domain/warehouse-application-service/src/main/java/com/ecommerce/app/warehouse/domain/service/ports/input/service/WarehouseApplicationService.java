package com.ecommerce.app.warehouse.domain.service.ports.input.service;


import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;
import com.ecommerce.app.warehouse.domain.core.entity.StockTransfer;
import com.ecommerce.app.warehouse.domain.service.dto.create.*;

import java.util.List;


public interface WarehouseApplicationService {
    WarehouseResponse getWarehouseBydId(WarehouseIdQuery warehouseIdQuery);
    List<WarehouseResponse> getAllWarehouses();
    CreateWarehouseResponse createWarehouse(CreateWarehouseCommand command);

    StockResponse findStockById(StockIdQuery stockIdQuery, AuthorizationHeader authorizationHeader);
    StockRequestedResponse updateStock(StockRequestCommand stockRequestCommand, AuthorizationHeader authorizationHeader);
    StockRequestedResponse createStock(StockRequestCommand stockRequestCommand, AuthorizationHeader authorizationHeader);

    List<StockJournalResponse> getStockJournalsByWarehouseId(ProductIdQuery productIdQuery, WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader);
    List<StockTransferResponse> findAllStockTransferBySourceWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader);
    List<StockTransferResponse> findAllStockTransferByDestinationWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader);
}
