package com.ecommerce.app.order.application.service.ports.output;

import com.ecommerce.app.order.domain.core.entity.Warehouse;

import java.util.List;

public interface WarehouseRepository {
    List<Warehouse> findAll();
    List<Warehouse> findAllWithProducts();
    List<Warehouse> findAllWithProductsAndStocks();
}
