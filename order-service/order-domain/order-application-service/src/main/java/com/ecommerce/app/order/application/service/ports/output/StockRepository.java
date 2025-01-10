package com.ecommerce.app.order.application.service.ports.output;

import com.ecommerce.app.common.domain.valueobject.StockId;
import com.ecommerce.app.order.domain.core.entity.Stock;

import java.util.Optional;

public interface StockRepository {
    Optional<Stock> findById(StockId stockId);
    Stock save(Stock stock);
}
