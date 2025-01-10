package com.ecommerce.app.product.application.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DetailProductResponse {
    private final UUID productId;
    private final String sku;
    private final String name;
    private final double price;
    private final String description;
    private final String imageUrl;
}
