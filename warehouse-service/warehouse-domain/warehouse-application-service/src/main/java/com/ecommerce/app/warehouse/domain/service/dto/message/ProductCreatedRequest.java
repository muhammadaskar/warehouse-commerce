package com.ecommerce.app.warehouse.domain.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class ProductCreatedRequest {
    private final String productId;
    private final String name;
    private final String imageUrl;
    private final BigDecimal price;
}
