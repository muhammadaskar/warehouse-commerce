package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockId;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Stock extends BaseEntity<StockId> {
    private WarehouseId warehouseId;
    private ProductId productId;
    private int quantity;
    private StockTransferStatus stockTransferStatus;
    private Warehouse warehouse;
    private StockJournal stockJournal;
    private Product product;

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
        stockTransferStatus = builder.stockTransferStatus;
        warehouse = builder.warehouse;
        stockJournal = builder.stockJournal;
        product = builder.product;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void createStock(Warehouse warehouse, Product product, int quantity, StockJournal stockJournal) {
        if (quantity < 0) {
            throw new WarehouseDomainException("Quantity cannot be negative");
        }
        super.setId(new StockId(UUID.randomUUID()));
        this.warehouse = warehouse;
        this.product = product;
        this.warehouseId = warehouse.getId();
        this.productId = stockJournal.getProductId();
        stockJournal.addStock(quantity);
        setQuantity(quantity);
    }

    public void updateStock(Product product, int quantity, StockJournal stockJournal) {
        if (quantity < 0) {
            throw new WarehouseDomainException("Quantity cannot be negative");
        }

        if (this.quantity > quantity) {
            stockJournal.reduceStock(this.quantity - quantity);
        } else {
            stockJournal.addStock(quantity - this.quantity);
        }
        setQuantity(quantity);
        this.product = product;
    }

    public void increase(int quantity) {
        this.quantity += quantity;
        System.out.println("Quantity increased to: " + this.quantity);
    }

    public void decrease(int quantity) {
        if (this.quantity < quantity) {
            throw new WarehouseDomainException("Insufficient stock to decrease");
        }
        this.quantity -= quantity;
        System.out.println("Quantity decreased to: " + this.quantity);
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
            stockTransferStatus = StockTransferStatus.TRANSFERRED;
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

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public StockJournal getStockJournal() {
        return stockJournal;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static final class Builder {
        private StockId id;
        private WarehouseId warehouseId;
        private ProductId productId;
        private int quantity;
        private ZonedDateTime updatedAt;
        private StockTransferStatus stockTransferStatus;
        private Warehouse warehouse;
        private StockJournal stockJournal;
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

        public Builder withStockTransferStatus(StockTransferStatus val) {
            stockTransferStatus = val;
            return this;
        }

        public Builder withWarehouse(Warehouse val) {
            warehouse = val;
            return this;
        }

        public Builder withStockJournal(StockJournal val) {
            stockJournal = val;
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
