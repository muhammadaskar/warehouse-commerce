package com.ecommerce.app.order.application.service;

import com.ecommerce.app.order.application.service.dto.message.StockCreated;
import com.ecommerce.app.order.application.service.dto.message.StockShippedUpdate;
import com.ecommerce.app.order.application.service.dto.message.StockTransferredUpdate;
import com.ecommerce.app.order.application.service.dto.message.StockUpdated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockHandler {

    private final StockHelper stockHelper;

    public StockHandler(StockHelper stockHelper) {
        this.stockHelper = stockHelper;
    }

    public void updateStockFromTransfer(StockTransferredUpdate stockTransferredUpdate) {
        stockHelper.updateStockFromTransfer(stockTransferredUpdate);
    }

    public void stockShippedUpdate(StockShippedUpdate stockShippedUpdate) {
        stockHelper.updateStockShipped(stockShippedUpdate);
    }

    public void updateStock(StockUpdated stockUpdated) {
        stockHelper.updateStock(stockUpdated);
    }

    public void createStock(StockCreated stockCreated) {
        stockHelper.createStock(stockCreated);
    }
}
