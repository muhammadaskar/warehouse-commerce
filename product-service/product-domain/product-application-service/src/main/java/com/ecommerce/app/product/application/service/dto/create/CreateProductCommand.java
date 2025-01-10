package com.ecommerce.app.product.application.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateProductCommand {
    private String name;
    private String description;
    private double price;
    private String imageUrl;
}
