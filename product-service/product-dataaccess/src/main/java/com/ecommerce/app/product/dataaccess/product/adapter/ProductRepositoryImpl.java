package com.ecommerce.app.product.dataaccess.product.adapter;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.product.application.service.ports.output.ProductRepository;
import com.ecommerce.app.product.dataaccess.product.mapper.ProductDataAccessMapper;
import com.ecommerce.app.product.dataaccess.product.repository.ProductJpaRepository;
import com.ecommerce.app.product.domain.core.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductDataAccessMapper productDataAccessMapper;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository, ProductDataAccessMapper productDataAccessMapper) {
        this.productJpaRepository = productJpaRepository;
        this.productDataAccessMapper = productDataAccessMapper;
    }

    @Override
    public Product save(Product product) {
        return productDataAccessMapper.productEntityToProduct(
                productJpaRepository.save(productDataAccessMapper.productToProductEntity(product)));
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productJpaRepository.findByName(name).map(productDataAccessMapper::productEntityToProduct);
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return productJpaRepository.findById(id.getValue()).map(productDataAccessMapper::productEntityToProduct);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(productDataAccessMapper::productEntityToProduct)
                .toList();
    }

}
