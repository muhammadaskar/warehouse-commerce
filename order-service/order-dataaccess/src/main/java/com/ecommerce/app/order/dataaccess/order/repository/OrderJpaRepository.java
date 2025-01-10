package com.ecommerce.app.order.dataaccess.order.repository;

import com.ecommerce.app.order.dataaccess.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findAllByUserId(UUID userId);
    List<OrderEntity> findAllByWarehouseId(UUID warehouseId);
}
