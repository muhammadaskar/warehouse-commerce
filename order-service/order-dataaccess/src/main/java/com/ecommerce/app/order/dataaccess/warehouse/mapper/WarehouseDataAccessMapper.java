package com.ecommerce.app.order.dataaccess.warehouse.mapper;

import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.order.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.order.dataaccess.stock.entity.StockEntity;
import com.ecommerce.app.order.dataaccess.warehouse.entity.WarehouseEntity;
import com.ecommerce.app.order.domain.core.entity.Product;
import com.ecommerce.app.order.domain.core.entity.Stock;
import com.ecommerce.app.order.domain.core.entity.Warehouse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WarehouseDataAccessMapper {

    public WarehouseEntity warehouseToWarehouseEntity(Warehouse warehouse) {
        return WarehouseEntity.builder()
                .id(warehouse.getId().getValue())
                .name(warehouse.getName())
                .city(warehouse.getAddress().getCity())
                .street(warehouse.getAddress().getStreet())
                .postalCode(warehouse.getAddress().getPostalCode())
                .latitude(warehouse.getAddress().getLatitude())
                .longitude(warehouse.getAddress().getLongitude())
                .stockEntities(stockToStockEntity(warehouse.getStocks()))
                .build();
    }

    public Warehouse warehouseEntityToWarehouse(WarehouseEntity warehouseEntity) {
        return Warehouse.builder()
                .withId(new WarehouseId(warehouseEntity.getId()))
                .withName(warehouseEntity.getName())
                .withAddress(addressEntityToAddress(warehouseEntity))
                .withStocks(stockEntityToStock(warehouseEntity.getStockEntities()))
                .build();
    }

    private Address addressEntityToAddress(WarehouseEntity warehouseEntity) {
        return new Address(
                warehouseEntity.getStreet(),
                warehouseEntity.getPostalCode(),
                warehouseEntity.getCity(),
                warehouseEntity.getLatitude(),
                warehouseEntity.getLongitude()
        );
    }

    public List<ProductEntity> productToProductEntity(List<Product> products) {
        return products.stream()
                .map(product -> ProductEntity.builder()
                        .id(product.getId().getValue())
                        .sku(product.getSku())
                        .price(product.getPrice().getAmount())
                        .stock(stockToStockEntity(product.getStocks()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Product> productEntityToProduct(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(productEntity -> Product.newBuilder()
                        .withId(new ProductId(productEntity.getId()))
                        .withSku(productEntity.getSku())
                        .withPrice(new Money(productEntity.getPrice()))
//                        .withStocks(stockEntityToStock(productEntity.getStock()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<Stock> stockEntityToStock(List<StockEntity> stockEntities) {
        return stockEntities.stream()
                .map(stockEntity -> Stock.builder()
                        .withId(new StockId(stockEntity.getId()))
                        .withProductId(new ProductId(stockEntity.getProduct().getId()))
                        .withWarehouseId(new WarehouseId(stockEntity.getWarehouse().getId()))
                        .withQuantity(stockEntity.getQuantity())
//                        .withProducts(productEntityToProduct(List.of(stockEntity.getProduct())))
                        .build())
                .collect(Collectors.toList());
    }

    private List<StockEntity> stockToStockEntity(List<Stock> stocks) {
        return stocks.stream()
                .map(stock -> StockEntity.builder()
                        .id(stock.getId().getValue())
                        .quantity(stock.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }
}
