package com.ecommerce.app.warehouse.domain.service.mapper;

import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.service.dto.create.*;
import org.springframework.stereotype.Component;

@Component
public class WarehouseDataMapper {

    public Warehouse warehouseCommandToWarehouse(CreateWarehouseCommand command) {
        return Warehouse.builder()
                .withName(command.getName())
                .withAddress(warehouseAddressToStreetAddress(command))
                .build();
    }

    public CreateWarehouseResponse warehouseToCreateWarehouseResponse(Warehouse warehouse, String message) {
        return CreateWarehouseResponse.builder()
                .warehouseId(warehouse.getId().getValue())
                .warehouseName(warehouse.getName())
                .warehouseAddress(warehouse.getAddress())
                .message(message)
                .build();
    }

    public Stock transferCommandToStock(StockTransferCommand command) {
        return new Stock(
                new WarehouseId(command.getWarehouseId()),
                new ProductId(command.getProductId()),
                command.getQuantity());
    }

    public StockTransferResponse stockTransferToStockResponse(Stock stock, String message) {
        return StockTransferResponse.builder()
                .warehouseId(stock.getWarehouseId().getValue())
                .productId(stock.getProductId().getValue())
                .status(stock.getStockTransferStatus())
                .message(message)
                .build();
    }

    private Address warehouseAddressToStreetAddress(CreateWarehouseCommand createWarehouseCommand) {
        return new Address(
                createWarehouseCommand.getStreet(),
                createWarehouseCommand.getPostalCode(),
                createWarehouseCommand.getCity()
        );
    }
}
