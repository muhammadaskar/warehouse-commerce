package com.ecommerce.app.warehouse.dataaccess.stock.mapper;

import com.ecommerce.app.common.domain.valueobject.Money;
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

@Component
public class StockDataAccessMapper {

    public StockEntity stockToStockEntity(Stock stock) {
        return StockEntity.builder()
                .id(stock.getStockId().getValue())
                .warehouse(warehouseToWarehouseEntity(stock.getWarehouse()))
                .product(ProductEntity.builder()
                        .id(stock.getProductId().getValue())
                        .name(stock.getProduct().getName())
                        .imageUrl(stock.getProduct().getImageUrl())
                        .price(stock.getProduct().getPrice().getAmount())
                        .build())
                .quantity(stock.getQuantity())
                .build();
    }

    public Stock stockEntityToStock(StockEntity stockEntity) {
        return Stock.builder()
                .withId(new StockId(stockEntity.getId()))
                .withWarehouseId(new WarehouseId(stockEntity.getWarehouse().getId()))
                .withWarehouse(Warehouse.builder()
                        .withId(new WarehouseId(stockEntity.getWarehouse().getId()))
                        .withName(stockEntity.getWarehouse().getName())
                        .build())
                .withProductId(new ProductId(stockEntity.getProduct().getId()))
                .withProduct(productEntityToProduct(stockEntity.getProduct()))
                .withQuantity(stockEntity.getQuantity())
                .build();
    }

    private WarehouseEntity warehouseToWarehouseEntity(Warehouse warehouse) {
        return WarehouseEntity.builder()
                .id(warehouse.getId().getValue())
                .name(warehouse.getName())
                .build();
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.newBuilder()
                .withId(new ProductId(productEntity.getId()))
                .withName(productEntity.getName())
                .withImageUrl(productEntity.getImageUrl())
                .withPrice(new Money(productEntity.getPrice()))
                .build();
    }
}
