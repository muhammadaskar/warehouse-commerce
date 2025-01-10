package com.ecommerce.app.warehouse.dataaccess.transfer.entity;

import com.ecommerce.app.warehouse.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.warehouse.dataaccess.warehouse.entity.WarehouseEntity;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_transfers")
@Entity
public class StockTransferEntity {
    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "source_warehouse_id", nullable = false)
    private WarehouseEntity sourceWarehouse; // Warehouse asal

    @OneToOne
    @JoinColumn(name = "destination_warehouse_id", nullable = false)
    private WarehouseEntity destinationWarehouse; // Warehouse tujuan

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    private int quantity;

    private String reason;

    @Enumerated(EnumType.STRING)
    private StockTransferStatus status;

    private ZonedDateTime createdAt;
}
