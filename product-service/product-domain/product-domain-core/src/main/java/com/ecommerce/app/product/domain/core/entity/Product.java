package com.ecommerce.app.product.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.product.domain.core.exception.ProductException;

import java.util.UUID;

public class Product extends BaseEntity<ProductId> {
    private final String name;
    private String sku;
    private final String description;
    private final double price;
    private final String imageUrl;

    public Product(String name, String sku, String description, double price, String imageUrl) {
        this.name = name;
        this.sku = sku;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private Product(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        sku = builder.sku;
        description = builder.description;
        price = builder.price;
        imageUrl = builder.imageUrl;
    }

    public void initializeProduct() {
        setId(new ProductId(UUID.randomUUID()));
        setSku("SKU-" + UUID.randomUUID().toString().substring(0, 3));
        validateProduct();
    }

    private void validateProduct() {
        if (name == null) {
            throw new ProductException("Product name is required");
        }
        if (description == null) {
            throw new ProductException("Product description is required");
        }
        if (price <= 0) {
            throw new ProductException("Product price must be greater than zero");
        }
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductId id;
        private String name;
        private String sku;
        private String description;
        private double price;
        private String imageUrl;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(ProductId val) {
            id = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withSku(String val) {
            sku = val;
            return this;
        }

        public Builder withDescription(String val) {
            description = val;
            return this;
        }

        public Builder withPrice(double val) {
            price = val;
            return this;
        }

        public Builder withImageUrl(String val) {
            imageUrl = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
