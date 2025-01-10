package com.ecommerce.app.warehouse.dataaccess.journal.repository;

import com.ecommerce.app.warehouse.dataaccess.journal.entity.StockJournalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StockJournalJpaRepository extends JpaRepository<StockJournalEntity, UUID> {
    List<StockJournalEntity> findAllByProductIdAndWarehouseId(UUID productId, UUID warehouseId);
}
