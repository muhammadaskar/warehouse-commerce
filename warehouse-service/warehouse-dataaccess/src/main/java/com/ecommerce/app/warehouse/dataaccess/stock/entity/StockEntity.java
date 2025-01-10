package com.ecommerce.app.warehouse.dataaccess.stock.entity;

import com.ecommerce.app.warehouse.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.warehouse.dataaccess.warehouse.entity.WarehouseEntity;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stocks")
@Entity
public class StockEntity {
    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity product;
//    private UUID productId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "WAREHOUSE_ID")
    private WarehouseEntity warehouse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntity that = (StockEntity) o;
        return quantity == that.quantity && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity);
    }
}
