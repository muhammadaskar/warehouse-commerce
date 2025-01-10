package com.ecommerce.app.warehouse.dataaccess.product.repository;

import com.ecommerce.app.warehouse.dataaccess.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
}
