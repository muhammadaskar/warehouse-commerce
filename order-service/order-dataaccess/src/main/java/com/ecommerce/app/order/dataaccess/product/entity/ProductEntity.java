package com.ecommerce.app.order.dataaccess.product.entity;

import com.ecommerce.app.order.dataaccess.order.entity.OrderItemEntity;
import com.ecommerce.app.order.dataaccess.stock.entity.StockEntity;
import com.ecommerce.app.order.dataaccess.warehouse.entity.WarehouseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Entity
public class ProductEntity {
    @Id
    private UUID id;
    private String sku;
    private BigDecimal price;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StockEntity> stock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
