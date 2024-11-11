package com.ecommerce.app.warehouse.dataaccess.stock.repository;

import com.ecommerce.app.warehouse.dataaccess.stock.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, UUID> {

    Optional<StockEntity> findById(UUID id);
}
