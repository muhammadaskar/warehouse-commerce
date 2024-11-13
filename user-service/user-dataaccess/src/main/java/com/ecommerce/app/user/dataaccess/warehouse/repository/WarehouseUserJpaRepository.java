package com.ecommerce.app.user.dataaccess.warehouse.repository;

import com.ecommerce.app.user.dataaccess.warehouse.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarehouseUserJpaRepository extends JpaRepository<WarehouseEntity, UUID> {
}
