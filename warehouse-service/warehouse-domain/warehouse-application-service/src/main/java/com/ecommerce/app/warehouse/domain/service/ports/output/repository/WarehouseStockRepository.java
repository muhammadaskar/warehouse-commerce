package com.ecommerce.app.warehouse.domain.service.ports.output.repository;

import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockId;

import java.util.Optional;

public interface WarehouseStockRepository {
    Stock save(Stock stock);

    Optional<Stock> findById(StockId stockId);
}
