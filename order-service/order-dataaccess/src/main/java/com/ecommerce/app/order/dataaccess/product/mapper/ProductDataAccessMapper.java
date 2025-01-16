package com.ecommerce.app.order.dataaccess.product.mapper;

import com.ecommerce.app.common.domain.valueobject.Money;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.StockId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.order.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.order.dataaccess.stock.entity.StockEntity;
import com.ecommerce.app.order.domain.core.entity.Product;
import com.ecommerce.app.order.domain.core.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDataAccessMapper {

    public ProductEntity productToProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId().getValue())
                .sku(product.getSku())
                .price(product.getPrice().getAmount())
                .stock(stockToStockEntity(product.getStocks()))
                .build();
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.newBuilder()
                .withId(new ProductId(productEntity.getId()))
                .withSku(productEntity.getSku())
                .withPrice(new Money(productEntity.getPrice()))
                .withStocks(stockEntityToStock(productEntity.getStock()))
                .build();
    }

    public ProductEntity productToProductEntityWithoutStock(Product product) {
        return ProductEntity.builder()
                .id(product.getId().getValue())
                .sku(product.getSku())
                .price(product.getPrice().getAmount())
                .build();
    }

    public Product productEntityToProductWithoutStock(ProductEntity productEntity) {
        return Product.newBuilder()
                .withId(new ProductId(productEntity.getId()))
                .withSku(productEntity.getSku())
                .withPrice(new Money(productEntity.getPrice()))
                .build();
    }

    private List<Stock> stockEntityToStock(List<StockEntity> stockEntities) {
        return stockEntities.stream()
                .map(stockEntity -> Stock.builder()
                        .withId(new StockId(stockEntity.getId()))
                        .withProductId(new ProductId(stockEntity.getProduct().getId()))
                        .withWarehouseId(new WarehouseId(stockEntity.getWarehouse().getId()))
                        .withQuantity(stockEntity.getQuantity())
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
