package com.ecommerce.app.order.application.service.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateOrderCommand {
    private final UUID userId;
    @Null
    private final UUID cartId;
    private final BigDecimal totalPrice;
    @NotNull
    private final List<OrderItem> items;
    @NotNull
    private final OrderAddress shippingAddress;
}
