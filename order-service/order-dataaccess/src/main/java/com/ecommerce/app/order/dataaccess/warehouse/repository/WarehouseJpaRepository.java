package com.ecommerce.app.order.dataaccess.warehouse.repository;

import com.ecommerce.app.order.dataaccess.warehouse.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseEntity, UUID> {
}
