package com.ecommerce.app.product.application.service.mapper;

import com.ecommerce.app.product.application.service.dto.create.CreateProductCommand;
import com.ecommerce.app.product.application.service.dto.create.CreateProductResponse;
import com.ecommerce.app.product.application.service.dto.create.DetailProductResponse;
import com.ecommerce.app.product.application.service.dto.create.ListProductResponse;
import com.ecommerce.app.product.domain.core.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDataMapper {
    public Product productCommandToProduct(CreateProductCommand createProductCommand) {
        return Product.builder()
                .withName(createProductCommand.getName())
                .withDescription(createProductCommand.getDescription())
                .withPrice(createProductCommand.getPrice())
                .withImageUrl(createProductCommand.getImageUrl())
                .build();
    }

    public CreateProductResponse productToCreateProductResponse(Product product, String message) {
        return CreateProductResponse.builder()
                .productId(product.getId().getValue())
                .sku(product.getSku())
                .name(product.getName())
                .message(message)
                .build();
    }

    public ListProductResponse productToListProductResponse(Product product) {
        return ListProductResponse.builder()
                .productId(product.getId().getValue())
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public DetailProductResponse productToDetailProductResponse(Product product) {
        return DetailProductResponse.builder()
                .productId(product.getId().getValue())
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public List<ListProductResponse> productsToListProductResponse(List<Product> products) {
        return products.stream()
                .map(this::productToListProductResponse)
                .toList();
    }
}
