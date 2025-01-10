package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;
import com.ecommerce.app.warehouse.domain.core.entity.StockTransfer;
import com.ecommerce.app.warehouse.domain.core.event.StockCreatedEvent;
import com.ecommerce.app.warehouse.domain.core.event.StockUpdatedEvent;
import com.ecommerce.app.warehouse.domain.service.dto.create.*;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockCreatedMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockUpdatedMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.WarehouseStockTransferedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WarehouseStockHandler {
    private final WarehouseStockHelper warehouseStockHelper;
    private final WarehouseDataMapper warehouseDataMapper;
    private final WarehouseStockTransferedMessagePublisher warehouseStockTransferedMessagePublisher;
    private final StockUpdatedMessagePublisher stockUpdatedMessagePublisher;
    private final StockCreatedMessagePublisher stockCreatedMessagePublisher;

    public WarehouseStockHandler(WarehouseStockHelper warehouseStockHelper, WarehouseDataMapper warehouseDataMapper, WarehouseStockTransferedMessagePublisher warehouseStockTransferedMessagePublisher, StockUpdatedMessagePublisher stockUpdatedMessagePublisher, StockCreatedMessagePublisher stockCreatedMessagePublisher) {
        this.warehouseStockHelper = warehouseStockHelper;
        this.warehouseDataMapper = warehouseDataMapper;
        this.warehouseStockTransferedMessagePublisher = warehouseStockTransferedMessagePublisher;
        this.stockUpdatedMessagePublisher = stockUpdatedMessagePublisher;
        this.stockCreatedMessagePublisher = stockCreatedMessagePublisher;
    }

    public StockResponse findStockById(StockIdQuery stockIdQuery, AuthorizationHeader authorizationHeader) {
        return warehouseDataMapper.stockToStockResponse(warehouseStockHelper.findStockById(stockIdQuery, authorizationHeader));
    }

    public StockRequestedResponse updateStock(StockRequestCommand stockRequestCommand, AuthorizationHeader authorizationHeader) {
        StockUpdatedEvent stockUpdatedEvent = warehouseStockHelper.updateStock(stockRequestCommand, authorizationHeader);
        stockUpdatedMessagePublisher.publish(stockUpdatedEvent);
        return warehouseDataMapper.stockToStockRequestedResponse(stockUpdatedEvent.getStock(), "Stock updated successfully");
    }

    public StockRequestedResponse createStock(StockRequestCommand stockRequestCommand, AuthorizationHeader authorizationHeader) {
        StockCreatedEvent stockCreatedEvent = warehouseStockHelper.createStock(stockRequestCommand, authorizationHeader);
        stockCreatedMessagePublisher.publish(stockCreatedEvent);
        return warehouseDataMapper.stockToStockRequestedResponse(stockCreatedEvent.getStock(), "Stock created successfully");
    }

    public List<StockJournalResponse> findStockJournalsByWarehouseId(ProductIdQuery productIdQuery, WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader) {
        return warehouseDataMapper.stockJournalsToStockJournalResponses(warehouseStockHelper.findStockJournalsByWarehouseId(productIdQuery, warehouseIdQuery, authorizationHeader));
    }

    public List<StockTransferResponse> findAllStockTransferBySourceWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader) {
        return warehouseDataMapper.stockTransfersToStockTransferResponses(warehouseStockHelper.findStockTransfersBySourceWarehouseId(warehouseIdQuery, authorizationHeader));
    }

    public List<StockTransferResponse> findAllStockTransferByDestinationWarehouseId(WarehouseIdQuery warehouseIdQuery, AuthorizationHeader authorizationHeader) {
        return warehouseDataMapper.stockTransfersToStockTransferResponses(warehouseStockHelper.findStockTransfersByDestinationWarehouseId(warehouseIdQuery, authorizationHeader));
    }
}
