package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.Money;
import com.ecommerce.app.common.domain.valueobject.ProductId;

import java.util.Set;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private String imageUrl;
    private Money price;
    private Set<Stock> stocks;

    public Product(ProductId id) {
        super.setId(id);
    }

    private Product(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        imageUrl = builder.imageUrl;
        price = builder.price;
        stocks = builder.stocks;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Money getPrice() {
        return price;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductId id;
        private String name;
        private String imageUrl;
        private Money price;
        private Set<Stock> stocks;

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

        public Builder withImageUrl(String val) {
            imageUrl = val;
            return this;
        }

        public Builder withPrice(Money val) {
            price = val;
            return this;
        }

        public Builder withStocks(Set<Stock> val) {
            stocks = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
