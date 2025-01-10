package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferId;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public class StockTransfer extends BaseEntity<StockTransferId> {
    private final WarehouseId sourceWarehouseId;
    private final WarehouseId destinationWarehouseId;
    private final ProductId productId;
    private final int quantity;
    private final String reason;
    private final  StockTransferStatus status;
    private ZonedDateTime createdAt;

    private Warehouse sourceWarehouse;
    private Warehouse destinationWarehouse;
    private Product product;

    public StockTransfer(WarehouseId sourceWarehouseId, WarehouseId destinationWarehouseId, ProductId productId, int quantity) {
        super.setId(new StockTransferId(UUID.randomUUID()));
        this.sourceWarehouseId = sourceWarehouseId;
        this.destinationWarehouseId = destinationWarehouseId;
        this.productId = productId;
        this.quantity = quantity;
        this.reason = "stock transfer by system because stock is low";
        this.status = StockTransferStatus.TRANSFERRED;
        this.createdAt = ZonedDateTime.now();
    }

    public WarehouseId getSourceWarehouseId() {
        return sourceWarehouseId;
    }

    public WarehouseId getDestinationWarehouseId() {
        return destinationWarehouseId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getReason() {
        return reason;
    }

    public StockTransferStatus getStatus() {
        return status;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Warehouse getSourceWarehouse() {
        return sourceWarehouse;
    }

    public Warehouse getDestinationWarehouse() {
        return destinationWarehouse;
    }

    public Product getProduct() {
        return product;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private StockTransfer(Builder builder) {
        super.setId(builder.id);
        sourceWarehouseId = builder.sourceWarehouseId;
        destinationWarehouseId = builder.destinationWarehouseId;
        productId = builder.productId;
        quantity = builder.quantity;
        reason = builder.reason;
        status = builder.status;
        createdAt = builder.createdAt;
        sourceWarehouse = builder.sourceWarehouse;
        destinationWarehouse = builder.destinationWarehouse;
        product = builder.product;
    }

    public static final class Builder {
        private StockTransferId id;
        private WarehouseId sourceWarehouseId;
        private WarehouseId destinationWarehouseId;
        private ProductId productId;
        private int quantity;
        private String reason;
        private StockTransferStatus status;
        private ZonedDateTime createdAt;
        private Warehouse sourceWarehouse;
        private Warehouse destinationWarehouse;
        private Product product;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(StockTransferId val) {
            id = val;
            return this;
        }

        public Builder withSourceWarehouseId(WarehouseId val) {
            sourceWarehouseId = val;
            return this;
        }

        public Builder withDestinationWarehouseId(WarehouseId val) {
            destinationWarehouseId = val;
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

        public Builder withReason(String val) {
            reason = val;
            return this;
        }

        public Builder withStatus(StockTransferStatus val) {
            status = val;
            return this;
        }

        public Builder withCreatedAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder withSourceWarehouse(Warehouse val) {
            sourceWarehouse = val;
            return this;
        }

        public Builder withDestinationWarehouse(Warehouse val) {
            destinationWarehouse = val;
            return this;
        }

        public Builder withProduct(Product val) {
            product = val;
            return this;
        }

        public StockTransfer build() {
            return new StockTransfer(this);
        }
    }
}
