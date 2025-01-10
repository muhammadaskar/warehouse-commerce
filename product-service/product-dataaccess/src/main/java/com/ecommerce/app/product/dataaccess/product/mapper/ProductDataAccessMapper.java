package com.ecommerce.app.product.dataaccess.product.mapper;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.product.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.product.domain.core.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDataAccessMapper {
    public ProductEntity productToProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId().getValue())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.builder()
                .withId(new ProductId(productEntity.getId()))
                .withSku(productEntity.getSku())
                .withName(productEntity.getName())
                .withDescription(productEntity.getDescription())
                .withPrice(productEntity.getPrice())
                .withImageUrl(productEntity.getImageUrl())
                .build();
    }
}
