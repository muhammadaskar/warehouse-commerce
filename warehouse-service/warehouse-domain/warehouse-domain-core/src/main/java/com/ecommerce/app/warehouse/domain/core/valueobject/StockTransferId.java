package com.ecommerce.app.warehouse.domain.core.valueobject;

import com.ecommerce.app.common.domain.valueobject.BaseId;

import java.util.UUID;

public class StockTransferId extends BaseId<UUID> {
    public StockTransferId(UUID value) {
        super(value);
    }
}
