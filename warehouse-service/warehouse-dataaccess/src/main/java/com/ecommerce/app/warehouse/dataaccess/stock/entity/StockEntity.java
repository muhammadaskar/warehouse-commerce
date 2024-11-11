package com.ecommerce.app.warehouse.dataaccess.stock.entity;

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
@Table(name = "orders")
@Entity
public class StockEntity {
    @Id
    private UUID id;
    private UUID warehouseId;
    private UUID productId;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private StockTransferStatus stockTransferStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntity that = (StockEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
