package com.ecommerce.app.warehouse.domain.service.dto.create;

import com.ecommerce.app.warehouse.domain.core.valueobject.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class StockJournalResponse {
    private final UUID stockJournalId;
    private final ChangeType changeType;
    private final int quantity;
    private final String reason;
    private final WarehouseResponse warehouse;
    private final ProductResponse product;
    private String createdAt;
}
