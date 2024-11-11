package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseResponse;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockTransferCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockTransferResponse;
import com.ecommerce.app.warehouse.domain.service.ports.input.service.WarehouseApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class WarehouseApplicationServiceImpl implements WarehouseApplicationService {

    private final WarehouseCreateCommandHandler warehouseCreateCommandHandler;
    private final WarehouseStockCommandHandler warehouseStockCommandHandler;

    public WarehouseApplicationServiceImpl(WarehouseCreateCommandHandler warehouseCreateCommandHandler, WarehouseStockCommandHandler warehouseStockCommandHandler) {
        this.warehouseCreateCommandHandler = warehouseCreateCommandHandler;
        this.warehouseStockCommandHandler = warehouseStockCommandHandler;
    }

    @Override
    public CreateWarehouseResponse createWarehouse(CreateWarehouseCommand command) {
        return warehouseCreateCommandHandler.createWarehouse(command);
    }

    @Override
    public StockTransferResponse transferStock(StockTransferCommand stockTransferCommand) {
        return warehouseStockCommandHandler.transferStock(stockTransferCommand);
    }
}
