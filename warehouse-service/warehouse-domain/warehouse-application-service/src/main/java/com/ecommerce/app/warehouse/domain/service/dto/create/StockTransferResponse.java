package com.ecommerce.app.warehouse.domain.service.dto.create;

import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class StockTransferResponse {
    @NotNull
    private final UUID warehouseId;
    @NotNull
    private final UUID productId;
    @NotNull
    private final StockTransferStatus status;
    @NotNull
    private final String message;
}
