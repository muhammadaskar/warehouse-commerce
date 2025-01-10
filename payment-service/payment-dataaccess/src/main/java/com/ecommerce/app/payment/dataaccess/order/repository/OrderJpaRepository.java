package com.ecommerce.app.payment.dataaccess.order.repository;

import com.ecommerce.app.payment.dataaccess.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
}
