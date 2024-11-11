package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.core.event.StockTransferredEvent;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockTransferCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockTransferResponse;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.WarehouseStockTransferedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarehouseStockCommandHandler {
    private final WarehouseStockCreateHelper warehouseStockCreateHelper;
    private final WarehouseDataMapper warehouseDataMapper;
    private final WarehouseStockTransferedMessagePublisher warehouseStockTransferedMessagePublisher;

    public WarehouseStockCommandHandler(WarehouseStockCreateHelper warehouseStockCreateHelper, WarehouseDataMapper warehouseDataMapper, WarehouseStockTransferedMessagePublisher warehouseStockTransferedMessagePublisher) {
        this.warehouseStockCreateHelper = warehouseStockCreateHelper;
        this.warehouseDataMapper = warehouseDataMapper;
        this.warehouseStockTransferedMessagePublisher = warehouseStockTransferedMessagePublisher;
    }

    public StockTransferResponse transferStock(StockTransferCommand stockTransferCommand) {
        StockTransferredEvent stockTransferredEvent = warehouseStockCreateHelper.persistTransfer(stockTransferCommand);
        warehouseStockTransferedMessagePublisher.publish(stockTransferredEvent);
        return warehouseDataMapper.stockTransferToStockResponse(stockTransferredEvent.getStock(),
                "Stock transferred successfully");
    }
}
