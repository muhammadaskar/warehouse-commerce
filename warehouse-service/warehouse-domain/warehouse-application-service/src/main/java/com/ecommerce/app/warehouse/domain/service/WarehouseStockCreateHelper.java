package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.WarehouseDomainService;
import com.ecommerce.app.warehouse.domain.core.entity.Product;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.event.StockTransferredEvent;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockTransferCommand;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.WarehouseStockTransferedMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class WarehouseStockCreateHelper {
    private final WarehouseDomainService warehouseDomainService;
    private final WarehouseStockRepository warehouseStockRepository;
    private final WarehouseDataMapper warehouseDataMapper;
    private final WarehouseStockTransferedMessagePublisher warehouseStockTransferedMessagePublisher;

    public WarehouseStockCreateHelper(WarehouseDomainService warehouseDomainService,
                                      WarehouseStockRepository warehouseStockRepository,
                                      WarehouseDataMapper warehouseDataMapper,
                                      WarehouseStockTransferedMessagePublisher warehouseStockTransferedMessagePublisher) {
        this.warehouseDomainService = warehouseDomainService;
        this.warehouseStockRepository = warehouseStockRepository;
        this.warehouseDataMapper = warehouseDataMapper;
        this.warehouseStockTransferedMessagePublisher = warehouseStockTransferedMessagePublisher;
    }

    @Transactional
    public StockTransferredEvent persistTransfer(StockTransferCommand stockTransferCommand) {
        Stock stock = warehouseDataMapper.transferCommandToStock(stockTransferCommand);
        WarehouseId warehouseId = new WarehouseId(stockTransferCommand.getWarehouseId());
        Product product = new Product(new ProductId(stockTransferCommand.getProductId()));
        saveStock(stock);
        return warehouseDomainService.stockTransferredEvent(
                warehouseId,
                stock,
                product,
                stockTransferCommand.getQuantity(),
                warehouseStockTransferedMessagePublisher
        );
    }

    private Stock saveStock(Stock stock) {
        Stock stockResult = warehouseStockRepository.save(stock);
        if (stockResult == null) {
            log.error("Could not save stock");
            throw new WarehouseDomainException("Could not save stock");
        }
        return stockResult;
    }
}
