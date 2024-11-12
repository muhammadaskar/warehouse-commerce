package com.ecommerce.app.warehouse.dataaccess.warehouse.repository;

import com.ecommerce.app.warehouse.dataaccess.warehouse.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarehouseJpaRepository extends JpaRepository<WarehouseEntity, UUID> {

//    Optional<WarehouseEntity> findById(UUID id);
}
