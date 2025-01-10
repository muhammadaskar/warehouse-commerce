package com.ecommerce.app.warehouse.dataaccess.journal.mapper;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.dataaccess.journal.entity.StockJournalEntity;
import com.ecommerce.app.warehouse.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.warehouse.dataaccess.product.mapper.ProductDataAccessMapper;
import com.ecommerce.app.warehouse.dataaccess.warehouse.entity.WarehouseEntity;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockJournalId;
import org.springframework.stereotype.Component;

@Component
public class StockJournalDataAccessMapper {

    private final ProductDataAccessMapper productDataAccessMapper;

    public StockJournalDataAccessMapper(ProductDataAccessMapper productDataAccessMapper) {
        this.productDataAccessMapper = productDataAccessMapper;
    }

    public StockJournal stockJournalEntityToStockJournal(StockJournalEntity stockJournalEntity) {
        return StockJournal.newBuilder()
                .withId(new StockJournalId(stockJournalEntity.getId()))
                .withProduct(productDataAccessMapper.productEntityToProduct(stockJournalEntity.getProduct()))
                .withWarehouse(warehouseEntityToWarehouse(stockJournalEntity.getWarehouse()))
                .withWarehouseId(new WarehouseId(stockJournalEntity.getWarehouse().getId()))
                .withProductId(new ProductId(stockJournalEntity.getProduct().getId()))
                .withChangeType(stockJournalEntity.getChangeType())
                .withReason(stockJournalEntity.getReason())
                .withCreatedAt(stockJournalEntity.getCreatedAt())
                .withQuantity(stockJournalEntity.getQuantity())
                .build();
    }

    public Warehouse warehouseEntityToWarehouse(WarehouseEntity warehouseEntity) {
        return Warehouse.builder()
                .withId(new WarehouseId(warehouseEntity.getId()))
                .withName(warehouseEntity.getName())
                .build();
    }

    public StockJournalEntity stockJournalToStockJournalEntity(StockJournal stockJournal) {
        StockJournalEntity stockJournalEntity = new StockJournalEntity();
        stockJournalEntity.setId(stockJournal.getId().getValue());
        stockJournalEntity.setWarehouse(WarehouseEntity.builder()
                .id(stockJournal.getWarehouseId().getValue())
                .build());
        stockJournalEntity.setProduct(ProductEntity.builder()
                .id(stockJournal.getProductId().getValue())
                .build());
        stockJournalEntity.setQuantity(stockJournal.getQuantity());
        stockJournalEntity.setChangeType(stockJournal.getChangeType());
        stockJournalEntity.setReason(stockJournal.getReason());
        stockJournalEntity.setCreatedAt(stockJournal.getCreatedAt());
        return stockJournalEntity;
    }

}
