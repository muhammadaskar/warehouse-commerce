package com.ecommerce.app.warehouse.domain.service.ports.output.repository;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.entity.StockTransfer;
import com.ecommerce.app.warehouse.domain.service.dto.create.WarehouseIdQuery;

import java.util.List;

public interface StockTransferRepository {
    StockTransfer save(StockTransfer stockTransfer);
    List<StockTransfer> findAllBySourceWarehouseId(WarehouseId warehouseId);
    List<StockTransfer> findAllByDestinationWarehouseId(WarehouseId warehouseId);
}
