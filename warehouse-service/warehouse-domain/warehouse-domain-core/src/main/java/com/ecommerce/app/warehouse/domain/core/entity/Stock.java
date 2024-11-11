package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockId;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferStatus;

import java.time.ZonedDateTime;

public class Stock extends BaseEntity<StockId> {
    private final WarehouseId warehouseId;
    private final ProductId productId;
    private int quantity;
    private ZonedDateTime updatedAt;
    private StockTransferStatus stockTransferStatus; // TODO: revise

    public Stock(WarehouseId warehouseId, ProductId productId, int quantity) {
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.quantity = quantity;
    }

    private Stock(Builder builder) {
        super.setId(builder.id);
        warehouseId = builder.warehouseId;
        productId = builder.productId;
        quantity = builder.quantity;
        updatedAt = builder.updatedAt;
        stockTransferStatus = builder.stockTransferStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void increase(int quantity) {
        this.quantity += quantity;
        this.updatedAt = ZonedDateTime.now();
    }

    public void decrease(int quantity) {
        if (this.quantity < quantity) {
            throw new WarehouseDomainException("Insufficient stock to decrease");
        }
        this.quantity -= quantity;
        this.updatedAt = ZonedDateTime.now();
    }

    public void transferStockTo(WarehouseId toWarehouseId, Product product, int quantity) {
        try {
            Stock stock = hasStock(product);
            if (stock != null) {
                stock.decrease(quantity);
                receiveStockFrom(toWarehouseId, product, quantity);
            } else {
                stock = new Stock(toWarehouseId, product.getId(), quantity);
                stock.decrease(quantity);
                product.getStocks().add(stock);
            }
            stockTransferStatus = StockTransferStatus.TRANSFER;
        } catch (WarehouseDomainException e) {
            throw new WarehouseDomainException("Error transferring stock: " + e.getMessage());
        }
    }

    public void receiveStockFrom(WarehouseId fromWarehouseId, Product product, int quantity) {
        try {
            Stock stock = hasStock(product);
            if (stock != null) {
                stock.increase(quantity);
            } else {
                stock = new Stock(fromWarehouseId, product.getId(), quantity);
                stock.increase(quantity);
                product.getStocks().add(stock);
            }
        } catch (WarehouseDomainException e) {
            throw new WarehouseDomainException("Error receiving stock: " + e.getMessage());
        }
    }
    private static Stock hasStock(Product product) {
        return product.getStocks().stream()
                .filter(s -> s.productId.equals(product.getId()))
                .findFirst()
                .orElse(null);
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public StockId getStockId() {
        return super.getId();
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public StockTransferStatus getStockTransferStatus() {
        return stockTransferStatus;
    }


    public static final class Builder {
        private StockId id;
        private WarehouseId warehouseId;
        private ProductId productId;
        private int quantity;
        private ZonedDateTime updatedAt;
        private StockTransferStatus stockTransferStatus;

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

        public Builder withStockTransferStatus(StockTransferStatus val) {
            stockTransferStatus = val;
            return this;
        }

        public Stock build() {
            return new Stock(this);
        }
    }
}
