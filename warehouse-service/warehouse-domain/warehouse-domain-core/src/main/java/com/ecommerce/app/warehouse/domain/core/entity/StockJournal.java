package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.valueobject.ChangeType;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockJournalId;

import java.time.ZonedDateTime;

public class StockJournal extends BaseEntity<StockJournalId> {
    private final WarehouseId warehouseId;
    private final ProductId productId;
    private ChangeType changeType;
    private int quantity;
    private String reason;
    private final ZonedDateTime createdAt;
    private final Product product;
    private final Warehouse warehouse;

    public void addStock(int quantity) {
        changeType = ChangeType.ADDITION;
        this.quantity = quantity;
        this.reason = "Stock added";
    }

    public void reduceStock(int quantity) {
        changeType = ChangeType.REDUCTION;
        this.quantity = quantity;
        this.reason = "Stock reduced";
    }

    private StockJournal(Builder builder) {
        super.setId(builder.id);
        warehouseId = builder.warehouseId;
        productId = builder.productId;
        changeType = builder.changeType;
        quantity = builder.quantity;
        reason = builder.reason;
        createdAt = builder.createdAt;
        product = builder.product;
        warehouse = builder.warehouse;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getReason() {
        return reason;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private StockJournalId id;
        private WarehouseId warehouseId;
        private ProductId productId;
        private ChangeType changeType;
        private int quantity;
        private String reason;
        private ZonedDateTime createdAt;

        private Product product;
        private Warehouse warehouse;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(StockJournalId val) {
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

        public Builder withChangeType(ChangeType val) {
            changeType = val;
            return this;
        }

        public Builder withQuantity(int val) {
            quantity = val;
            return this;
        }

        public Builder withReason(String val) {
            reason = val;
            return this;
        }

        public Builder withCreatedAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder withProduct(Product val) {
            product = val;
            return this;
        }

        public Builder withWarehouse(Warehouse val) {
            warehouse = val;
            return this;
        }

        public StockJournal build() {
            return new StockJournal(this);
        }
    }
}
