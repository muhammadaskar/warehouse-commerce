package com.ecommerce.app.warehouse.dataaccess.transfer.repository;

import com.ecommerce.app.warehouse.dataaccess.transfer.entity.StockTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StockTransferJpaRepository extends JpaRepository<StockTransferEntity, UUID> {
    List<StockTransferEntity> findAllBySourceWarehouseId(UUID sourceWarehouseId);
    List<StockTransferEntity> findAllByDestinationWarehouseId(UUID destinationWarehouseId);
}
