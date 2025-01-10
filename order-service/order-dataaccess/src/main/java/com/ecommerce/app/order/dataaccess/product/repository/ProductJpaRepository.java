package com.ecommerce.app.order.dataaccess.product.repository;

import com.ecommerce.app.order.dataaccess.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.stock WHERE p.id = :productId")
    List<ProductEntity> findAllByProductIdWithStock(@Param("productId") UUID productId);
}
