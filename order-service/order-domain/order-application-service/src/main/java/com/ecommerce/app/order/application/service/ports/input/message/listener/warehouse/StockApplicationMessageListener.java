package com.ecommerce.app.order.application.service.ports.input.message.listener.warehouse;

import com.ecommerce.app.order.application.service.dto.message.StockCreated;
import com.ecommerce.app.order.application.service.dto.message.StockShippedUpdate;
import com.ecommerce.app.order.application.service.dto.message.StockTransferredUpdate;
import com.ecommerce.app.order.application.service.dto.message.StockUpdated;

public interface StockApplicationMessageListener {
    void updateStockFromTransfer(StockTransferredUpdate stockTransferredUpdate);
    void updateStockShipped(StockShippedUpdate stockShippedUpdate);
    void updateStock(StockUpdated stockUpdated);
    void createStock(StockCreated stockCreated);
}
