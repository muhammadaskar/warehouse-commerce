package com.ecommerce.app.order.application.service.dto.create;

import com.ecommerce.app.order.domain.core.entity.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrderItemResponse {
    @NotNull
    private final String id;
    @NotNull
    private final ProductResponse product;
    @NotNull
    private final Integer quantity;
    @NotNull
    private final Float price;
    @NotNull
    private final Float subTotal;
}
