package com.ecommerce.app.warehouse.domain.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class StockTransferCommand {
    @NotNull
    private final UUID warehouseId;
    @NotNull
    private final UUID productId;
    @NotNull
    private final int quantity;
}
