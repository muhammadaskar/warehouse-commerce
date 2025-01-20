package com.ecommerce.app.warehouse.dataaccess.product.mapper;

import com.ecommerce.app.common.domain.valueobject.Money;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.warehouse.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.warehouse.domain.core.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDataAccessMapper {

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.newBuilder()
                .withId(new ProductId(productEntity.getId()))
                .withName(productEntity.getName())
                .withImageUrl(productEntity.getImageUrl())
                .withPrice(new Money(productEntity.getPrice()))
                .build();
    }

    public ProductEntity productToProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId().getValue())
                .name(product.getName())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice().getAmount())
                .build();
    }
}
