package com.ecommerce.app.warehouse.domain.service.ports.output.repository;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockJournalId;

import java.util.List;
import java.util.Optional;

public interface StockJournalRepository {
    StockJournal save(StockJournal stockJournal);
    Optional<StockJournal> findById(StockJournalId stockJournalId);
    List<StockJournal> findAllByProductIdWarehouseId(ProductId productId, WarehouseId warehouseId);
}
