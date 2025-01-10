package com.ecommerce.app.order.application.service;

import com.ecommerce.app.order.application.service.dto.message.StockCreated;
import com.ecommerce.app.order.application.service.dto.message.StockShippedUpdate;
import com.ecommerce.app.order.application.service.dto.message.StockTransferredUpdate;
import com.ecommerce.app.order.application.service.dto.message.StockUpdated;
import com.ecommerce.app.order.application.service.ports.input.message.listener.warehouse.StockApplicationMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class StockApplicationMessageListenerImpl implements StockApplicationMessageListener {

    private final StockHandler stockHandler;

    public StockApplicationMessageListenerImpl(StockHandler stockHandler) {
        this.stockHandler = stockHandler;
    }

    @Override
    public void updateStockFromTransfer(StockTransferredUpdate stockTransferredUpdate) {
        stockHandler.updateStockFromTransfer(stockTransferredUpdate);
    }

    @Override
    public void updateStockShipped(StockShippedUpdate stockShippedUpdate) {
        stockHandler.stockShippedUpdate(stockShippedUpdate);
    }

    @Override
    public void updateStock(StockUpdated stockUpdated) {
        stockHandler.updateStock(stockUpdated);
    }

    @Override
    public void createStock(StockCreated stockCreated) {
        stockHandler.createStock(stockCreated);
    }
}
