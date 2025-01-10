package com.ecommerce.app.order.dataaccess.stock.repository;

import com.ecommerce.app.order.dataaccess.stock.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockJpaRepository extends JpaRepository<StockEntity, UUID> {
}
