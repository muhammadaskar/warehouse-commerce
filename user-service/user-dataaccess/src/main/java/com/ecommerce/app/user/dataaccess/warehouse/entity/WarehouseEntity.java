package com.ecommerce.app.user.dataaccess.warehouse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouses")
@Entity
public class WarehouseEntity {
    @Id
    private UUID warehouseId;
}
