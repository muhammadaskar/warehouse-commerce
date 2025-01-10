package com.ecommerce.app.order.dataaccess.product.adapter;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.order.application.service.ports.output.ProductRepository;
import com.ecommerce.app.order.dataaccess.product.mapper.ProductDataAccessMapper;
import com.ecommerce.app.order.dataaccess.product.repository.ProductJpaRepository;
import com.ecommerce.app.order.domain.core.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductDataAccessMapper productDataAccessMapper;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository, ProductDataAccessMapper productDataAccessMapper) {
        this.productJpaRepository = productJpaRepository;
        this.productDataAccessMapper = productDataAccessMapper;
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream().map(productDataAccessMapper::productEntityToProduct).toList();
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return productJpaRepository.findById(productId.getValue()).map(productDataAccessMapper::productEntityToProduct);
    }

    @Override
    public List<Product> findAllByProductIdWithStock(ProductId productId) {
        return productJpaRepository.findAllByProductIdWithStock(productId.getValue()).stream().map(productDataAccessMapper::productEntityToProduct).toList();
    }
}
