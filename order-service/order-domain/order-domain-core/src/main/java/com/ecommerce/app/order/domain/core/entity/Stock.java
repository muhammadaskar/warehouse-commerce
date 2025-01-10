package com.ecommerce.app.order.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.StockId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;

import java.time.ZonedDateTime;
import java.util.List;

public class Stock extends BaseEntity<StockId> {
    private final WarehouseId warehouseId;
    private final ProductId productId;
    private int quantity;
    private final ZonedDateTime updatedAt;
    private Product product;

    private Stock(Builder builder) {
        super.setId(builder.id);
        warehouseId = builder.warehouseId;
        productId = builder.productId;
        quantity = builder.quantity;
        updatedAt = builder.updatedAt;
        product = builder.product;
    }

    public void createStock(Product product) {
        this.product = product;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StockId id;
        private WarehouseId warehouseId;
        private ProductId productId;
        private int quantity;
        private ZonedDateTime updatedAt;
        private Product product;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(StockId val) {
            id = val;
            return this;
        }

        public Builder withWarehouseId(WarehouseId val) {
            warehouseId = val;
            return this;
        }

        public Builder withProductId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder withQuantity(int val) {
            quantity = val;
            return this;
        }

        public Builder withUpdatedAt(ZonedDateTime val) {
            updatedAt = val;
            return this;
        }

        public Builder withProduct(Product val) {
            product = val;
            return this;
        }

        public Stock build() {
            return new Stock(this);
        }
    }
}
