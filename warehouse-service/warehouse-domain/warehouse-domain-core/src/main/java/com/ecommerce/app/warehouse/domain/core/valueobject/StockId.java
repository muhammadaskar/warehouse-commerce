package com.ecommerce.app.warehouse.domain.core.valueobject;

import com.ecommerce.app.common.domain.valueobject.BaseId;

import java.util.UUID;

public class StockId extends BaseId<UUID> {
    public StockId(UUID value) {
        super(value);
    }
}
