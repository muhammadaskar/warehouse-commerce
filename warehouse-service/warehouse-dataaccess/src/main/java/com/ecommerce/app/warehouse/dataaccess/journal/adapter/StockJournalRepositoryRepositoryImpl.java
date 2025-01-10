package com.ecommerce.app.warehouse.dataaccess.journal.adapter;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.dataaccess.journal.entity.StockJournalEntity;
import com.ecommerce.app.warehouse.dataaccess.journal.mapper.StockJournalDataAccessMapper;
import com.ecommerce.app.warehouse.dataaccess.journal.repository.StockJournalJpaRepository;
import com.ecommerce.app.warehouse.dataaccess.product.repository.ProductJpaRepository;
import com.ecommerce.app.warehouse.dataaccess.warehouse.repository.WarehouseJpaRepository;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockJournalId;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.StockJournalRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StockJournalRepositoryRepositoryImpl implements StockJournalRepository {

    private final ProductJpaRepository productJpaRepository;
    private final WarehouseJpaRepository warehouseJpaRepository;
    private final StockJournalJpaRepository stockJournalJpaRepository;
    private final StockJournalDataAccessMapper stockJournalDataAccessMapper;

    public StockJournalRepositoryRepositoryImpl(ProductJpaRepository productJpaRepository, WarehouseJpaRepository warehouseJpaRepository, StockJournalJpaRepository stockJournalJpaRepository, StockJournalDataAccessMapper stockJournalDataAccessMapper) {
        this.productJpaRepository = productJpaRepository;
        this.warehouseJpaRepository = warehouseJpaRepository;
        this.stockJournalJpaRepository = stockJournalJpaRepository;
        this.stockJournalDataAccessMapper = stockJournalDataAccessMapper;
    }

    @Override
    public StockJournal save(StockJournal stockJournal) {
        var product = productJpaRepository.findById(stockJournal.getProductId().getValue())
                .orElseThrow(() -> new WarehouseDomainException("Product not found"));

        var warehouse = warehouseJpaRepository.findById(stockJournal.getWarehouseId().getValue())
                .orElseThrow(() -> new WarehouseDomainException("Warehouse not found"));

        StockJournalEntity stockJournalEntity = StockJournalEntity.builder()
                .id(stockJournal.getId().getValue())
                .product(product)
                .warehouse(warehouse)
                .quantity(stockJournal.getQuantity())
                .changeType(stockJournal.getChangeType())
                .reason(stockJournal.getReason())
                .createdAt(stockJournal.getCreatedAt())
                .build();

        return stockJournalDataAccessMapper.stockJournalEntityToStockJournal(stockJournalJpaRepository.save(stockJournalEntity));
    }

    @Override
    public Optional<StockJournal> findById(StockJournalId stockJournalId) {
        return stockJournalJpaRepository.findById(stockJournalId.getValue())
                .map(stockJournalDataAccessMapper::stockJournalEntityToStockJournal);
    }

    @Override
    public List<StockJournal> findAllByProductIdWarehouseId(ProductId productId, WarehouseId warehouseId) {
        return stockJournalJpaRepository.findAllByProductIdAndWarehouseId(productId.getValue(), warehouseId.getValue()).stream()
                .map(stockJournalDataAccessMapper::stockJournalEntityToStockJournal)
                .toList();
    }
}
