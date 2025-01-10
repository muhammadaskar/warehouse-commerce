package com.ecommerce.app.warehouse.dataaccess.transfer.adapter;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.dataaccess.product.repository.ProductJpaRepository;
import com.ecommerce.app.warehouse.dataaccess.transfer.entity.StockTransferEntity;
import com.ecommerce.app.warehouse.dataaccess.transfer.mapper.StockTransferDataAccessMapper;
import com.ecommerce.app.warehouse.dataaccess.transfer.repository.StockTransferJpaRepository;
import com.ecommerce.app.warehouse.dataaccess.warehouse.repository.WarehouseJpaRepository;
import com.ecommerce.app.warehouse.domain.core.entity.StockTransfer;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;
import com.ecommerce.app.warehouse.domain.service.dto.create.WarehouseIdQuery;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.StockTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StockTransferRepositoryImpl implements StockTransferRepository {

    private final StockTransferJpaRepository stockTranferJpaRepository;
    private final WarehouseJpaRepository warehouseJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final StockTransferDataAccessMapper stockTransferDataAccessMapper;

    public StockTransferRepositoryImpl(StockTransferJpaRepository stockTranferJpaRepository, WarehouseJpaRepository warehouseJpaRepository, ProductJpaRepository productJpaRepository, StockTransferDataAccessMapper stockTransferDataAccessMapper) {
        this.stockTranferJpaRepository = stockTranferJpaRepository;
        this.warehouseJpaRepository = warehouseJpaRepository;
        this.productJpaRepository = productJpaRepository;
        this.stockTransferDataAccessMapper = stockTransferDataAccessMapper;
    }

    @Override
    public StockTransfer save(StockTransfer stockTransfer) {
        var product = productJpaRepository.findById(stockTransfer.getProductId().getValue())
                .orElseThrow(() -> new WarehouseDomainException("Product not found"));

        var sourceWarehouse = warehouseJpaRepository.findById(stockTransfer.getSourceWarehouseId().getValue())
                .orElseThrow(() -> new WarehouseDomainException("Source Warehouse not found"));

        var destinationWarehouse = warehouseJpaRepository.findById(stockTransfer.getDestinationWarehouseId().getValue())
                .orElseThrow(() -> new WarehouseDomainException("Destination Warehouse not found"));

        StockTransferEntity stockTransferEntity = StockTransferEntity.builder()
                .id(stockTransfer.getId().getValue())
                .sourceWarehouse(sourceWarehouse)
                .destinationWarehouse(destinationWarehouse)
                .product(product)
                .quantity(stockTransfer.getQuantity())
                .reason(stockTransfer.getReason())
                .status(stockTransfer.getStatus())
                .createdAt(stockTransfer.getCreatedAt())
                .build();

        return stockTransferDataAccessMapper.stockTransferEntityToStockTransfer(stockTranferJpaRepository.save(stockTransferEntity));
    }

    @Override
    public List<StockTransfer> findAllBySourceWarehouseId(WarehouseId warehouseId) {
        return stockTranferJpaRepository.findAllBySourceWarehouseId(warehouseId.getValue())
                .stream()
                .map(stockTransferDataAccessMapper::stockTransferEntityToStockTransfer)
                .toList();
    }

    @Override
    public List<StockTransfer> findAllByDestinationWarehouseId(WarehouseId warehouseId) {
        return stockTranferJpaRepository.findAllByDestinationWarehouseId(warehouseId.getValue())
                .stream()
                .map(stockTransferDataAccessMapper::stockTransferEntityToStockTransfer)
                .toList();
    }
}
