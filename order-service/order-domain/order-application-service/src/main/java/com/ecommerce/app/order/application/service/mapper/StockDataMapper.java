package com.ecommerce.app.order.application.service.mapper;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.StockId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.order.application.service.dto.message.*;
import com.ecommerce.app.order.domain.core.entity.Stock;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class StockDataMapper {

    public Stock stockTransferredUpdateToStockWarehouseFrom(StockTransferredUpdate stockTransferredUpdate) {
        return Stock.builder()
                .withId(new StockId(UUID.fromString(stockTransferredUpdate.getStockIdWarehouseFrom())))
                .withQuantity(stockTransferredUpdate.getQuantityUpdatedFromWarehouse())
                .build();
    }

    public Stock stockTransferredUpdateToStockWarehouseTo(StockTransferredUpdate stockTransferredUpdate) {
        return Stock.builder()
                .withId(new StockId(UUID.fromString(stockTransferredUpdate.getStockIdWarehouseTo())))
                .withQuantity(stockTransferredUpdate.getQuantityUpdatedToWarehouse())
                .build();
    }

    public List<Stock> stockShippedUpdateToStock(StockShippedUpdate stockShippedUpdate) {
        List<StockItem> stockItems = stockShippedUpdate.getStockItems().stream().toList();
        return stockItems.stream()
                .map(stockItem -> Stock.builder()
                        .withId(new StockId(UUID.fromString(stockItem.getStockId())))
                        .withQuantity(stockItem.getQuantity())
                        .build())
                .toList();
    }

    public Stock stockUpdatedToStock(StockUpdated stockUpdated) {
        return Stock.builder()
                .withId(new StockId(UUID.fromString(stockUpdated.getStockId())))
                .withQuantity(stockUpdated.getQuantity())
                .withUpdatedAt(stockUpdated.getCreatedAt())
                .build();
    }

    public Stock stockCreatedToStock(StockCreated stockCreated) {
        return Stock.builder()
                .withId(new StockId(UUID.fromString(stockCreated.getStockId())))
                .withWarehouseId(new WarehouseId(UUID.fromString(stockCreated.getWarehouseId())))
                .withProductId(new ProductId(UUID.fromString(stockCreated.getProductId())))
                .withQuantity(stockCreated.getQuantity())
                .withUpdatedAt(stockCreated.getCreatedAt())
                .build();
    }
}
