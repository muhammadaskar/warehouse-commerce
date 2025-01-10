package com.ecommerce.app.warehouse.dataaccess.warehouse.entity;

import com.ecommerce.app.warehouse.dataaccess.stock.entity.StockEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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
    private UUID id;
    private String name;
    private String street;
    private String postalCode;
    private String city;
    private String latitude;
    private String longitude;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockEntity> stockEntities = new ArrayList<>();
}
