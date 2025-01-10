package com.ecommerce.app.order.dataaccess.stock.mapper;

import com.ecommerce.app.common.domain.valueobject.Money;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.StockId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.order.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.order.dataaccess.stock.entity.StockEntity;
import com.ecommerce.app.order.dataaccess.warehouse.entity.WarehouseEntity;
import com.ecommerce.app.order.domain.core.entity.Product;
import com.ecommerce.app.order.domain.core.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockDataAccessMapper {

    public Stock stockEntityToStockEntity(StockEntity stockEntity) {
        return Stock.builder()
                .withId(new StockId(stockEntity.getId()))
                .withQuantity(stockEntity.getQuantity())
                .withUpdatedAt(stockEntity.getUpdatedAt())
                .withProductId(new ProductId(stockEntity.getProduct().getId()))
                .withProduct(productEntityToProduct(stockEntity.getProduct()))
                .withWarehouseId(new WarehouseId(stockEntity.getWarehouse().getId()))
                .withUpdatedAt(stockEntity.getUpdatedAt())
                .build();
    }

    public StockEntity stockToStockEntity(Stock stock) {
        return StockEntity.builder()
                .id(stock.getId().getValue())
                .warehouse(WarehouseEntity.builder()
                        .id(stock.getWarehouseId().getValue())
                        .build())
                .product(productToProductEntity(stock.getProduct()))
                .quantity(stock.getQuantity())
                .updatedAt(stock.getUpdatedAt())
                .build();
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.newBuilder()
                .withId(new ProductId(productEntity.getId()))
                .withSku(productEntity.getSku())
                .withPrice(new Money(productEntity.getPrice()))
                .build();
    }

    public ProductEntity productToProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId().getValue())
                .sku(product.getSku())
                .price(product.getPrice().getAmount())
                .build();
    }

}
