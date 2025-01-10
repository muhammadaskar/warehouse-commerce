package com.ecommerce.app.warehouse.dataaccess.transfer.mapper;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.warehouse.dataaccess.transfer.entity.StockTransferEntity;
import com.ecommerce.app.warehouse.dataaccess.warehouse.entity.WarehouseEntity;
import com.ecommerce.app.warehouse.domain.core.entity.Product;
import com.ecommerce.app.warehouse.domain.core.entity.StockTransfer;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferId;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class StockTransferDataAccessMapper {

   public StockTransfer stockTransferEntityToStockTransfer(StockTransferEntity stockTransferEntity) {
        return StockTransfer.newBuilder()
                .withId(new StockTransferId(stockTransferEntity.getId()))
                .withSourceWarehouseId(new WarehouseId(stockTransferEntity.getSourceWarehouse().getId()))
                .withSourceWarehouse(warehouEntityToWarehouse(stockTransferEntity.getSourceWarehouse()))
                .withDestinationWarehouseId(new WarehouseId(stockTransferEntity.getDestinationWarehouse().getId()))
                .withDestinationWarehouse(warehouEntityToWarehouse(stockTransferEntity.getDestinationWarehouse()))
                .withProductId(new ProductId(stockTransferEntity.getProduct().getId()))
                .withProduct(productEntityToProduct(stockTransferEntity.getProduct()))
                .withQuantity(stockTransferEntity.getQuantity())
                .withReason(stockTransferEntity.getReason())
                .withStatus(stockTransferEntity.getStatus())
                .withCreatedAt(stockTransferEntity.getCreatedAt())
                .build();
    }

    public Warehouse warehouEntityToWarehouse(WarehouseEntity warehouseEntity) {
        return Warehouse.builder()
                .withId(new WarehouseId(warehouseEntity.getId()))
                .withName(warehouseEntity.getName())
                .build();
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.newBuilder()
                .withId(new ProductId(productEntity.getId()))
                .withName(productEntity.getName())
                .build();
    }
}
