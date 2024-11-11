package com.ecommerce.app.warehouse.domain.service.ports.input.service;


import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseResponse;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockTransferCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockTransferResponse;

import javax.validation.Valid;

public interface WarehouseApplicationService {
    CreateWarehouseResponse createWarehouse(@Valid CreateWarehouseCommand command);
    StockTransferResponse transferStock(@Valid StockTransferCommand stockTransferCommand);
}
