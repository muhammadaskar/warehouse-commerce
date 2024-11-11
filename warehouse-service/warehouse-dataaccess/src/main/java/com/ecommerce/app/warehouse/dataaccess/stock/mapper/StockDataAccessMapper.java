package com.ecommerce.app.warehouse.dataaccess.stock.mapper;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.dataaccess.stock.entity.StockEntity;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockId;
import org.springframework.stereotype.Component;

@Component
public class StockDataAccessMapper {

    public StockEntity stockToStockEntity(Stock stock) {
        return StockEntity.builder()
                .id(stock.getStockId().getValue())
                .warehouseId(stock.getWarehouseId().getValue())
                .productId(stock.getProductId().getValue())
                .quantity(stock.getQuantity())
                .build();
    }

    public Stock stockEntityToStock(StockEntity stockEntity) {
        return Stock.builder()
                .withId(new StockId(stockEntity.getId()))
                .withWarehouseId(new WarehouseId(stockEntity.getWarehouseId()))
                .withProductId(new ProductId(stockEntity.getProductId()))
                .withQuantity(stockEntity.getQuantity())
                .withStockTransferStatus(stockEntity.getStockTransferStatus())
                .build();
    }
}
