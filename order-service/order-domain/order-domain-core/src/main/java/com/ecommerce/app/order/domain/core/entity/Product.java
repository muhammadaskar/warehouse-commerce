package com.ecommerce.app.order.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.Money;
import com.ecommerce.app.common.domain.valueobject.ProductId;

import java.util.List;

public class Product extends BaseEntity<ProductId> {
    private final String sku;
    private final String name;
    private final String imageUrl;
    private final Money price;
    private List<Stock> stocks;

    private Product(Builder builder) {
        super.setId(builder.id);
        sku = builder.sku;
        name = builder.name;
        imageUrl = builder.imageUrl;
        price = builder.price;
        stocks = builder.stocks;
    }

    public String getSku() {
        return sku;
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

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductId id;
        private String sku;
        private String name;
        private String imageUrl;
        private Money price;
        private List<Stock> stocks;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(ProductId val) {
            id = val;
            return this;
        }

        public Builder withSku(String val) {
            sku = val;
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

        public Builder withStocks(List<Stock> val) {
            stocks = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
