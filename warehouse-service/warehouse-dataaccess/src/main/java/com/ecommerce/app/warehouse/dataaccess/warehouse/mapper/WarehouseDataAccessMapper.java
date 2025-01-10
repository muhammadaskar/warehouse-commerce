package com.ecommerce.app.warehouse.dataaccess.warehouse.mapper;

import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.warehouse.dataaccess.stock.entity.StockEntity;
import com.ecommerce.app.warehouse.dataaccess.warehouse.entity.WarehouseEntity;
import com.ecommerce.app.warehouse.domain.core.entity.Product;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WarehouseDataAccessMapper {

    public WarehouseEntity warehouseToWarehouseEntity(Warehouse warehouse) {
        return WarehouseEntity.builder()
                .id(warehouse.getId().getValue())
                .name(warehouse.getName())
                .street(warehouse.getAddress().getStreet())
                .postalCode(warehouse.getAddress().getPostalCode())
                .city(warehouse.getAddress().getCity())
                .latitude(warehouse.getAddress().getLatitude())
                .longitude(warehouse.getAddress().getLongitude())
                .build();
    }

    public Warehouse warehouseEntityToWarehouseEntity(WarehouseEntity warehouseEntity) {
        return Warehouse.builder()
                .withId(new WarehouseId(warehouseEntity.getId()))
                .withName(warehouseEntity.getName())
                .withAddress(warehouseAddressToStreetAddress(warehouseEntity))
                .build();
    }

    private Address warehouseAddressToStreetAddress(WarehouseEntity warehouseEntity) {
        return new Address(
                warehouseEntity.getStreet(),
                warehouseEntity.getPostalCode(),
                warehouseEntity.getCity(),
                warehouseEntity.getLatitude(),
                warehouseEntity.getLongitude()
        );
    }

    public WarehouseEntity warehouseToWarehouseEntityWithStocks(Warehouse warehouse) {
        return WarehouseEntity.builder()
                .id(warehouse.getId().getValue())
                .name(warehouse.getName())
                .street(warehouse.getAddress().getStreet())
                .postalCode(warehouse.getAddress().getPostalCode())
                .city(warehouse.getAddress().getCity())
                .latitude(warehouse.getAddress().getLatitude())
                .longitude(warehouse.getAddress().getLongitude())
                .stockEntities(stockToStockEntity(warehouse.getStocks()))
                .build();
    }

    public Warehouse warehouseEntityToWarehouseEntityWithStocks(WarehouseEntity warehouseEntity) {
        return Warehouse.builder()
                .withId(new WarehouseId(warehouseEntity.getId()))
                .withName(warehouseEntity.getName())
                .withAddress(warehouseAddressToStreetAddress(warehouseEntity))
                .withStocks(stockEntityToStocks(warehouseEntity.getStockEntities()))
                .build();
    }

    private List<Stock> stockEntityToStocks(List<StockEntity> stockEntities) {
        return stockEntities.stream()
                .map(stockEntity -> Stock.builder()
                        .withId(new StockId(stockEntity.getId()))
                        .withWarehouseId(new WarehouseId(stockEntity.getWarehouse().getId()))
                        .withQuantity(stockEntity.getQuantity())
                        .withProduct(productEntityToProduct(stockEntity.getProduct()))
                        .withProductId(new ProductId(stockEntity.getProduct().getId()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<StockEntity> stockToStockEntity(List<Stock> stocks) {
        return stocks.stream()
                .map(stock -> StockEntity.builder()
                        .id(stock.getId().getValue())
                        .quantity(stock.getQuantity())
                        .product(ProductEntity.builder().build())
                        .build())
                .collect(Collectors.toList());
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.newBuilder()
                .withId(new ProductId(productEntity.getId()))
                .withName(productEntity.getName())
                .build();
    }

    public ProductEntity productToProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId().getValue())
                .name(product.getName())
                .build();
    }
}
