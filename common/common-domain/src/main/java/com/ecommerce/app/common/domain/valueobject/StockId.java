package com.ecommerce.app.common.domain.valueobject;

import java.util.UUID;

public class StockId extends BaseId<UUID> {
    public StockId(UUID id) {
        super(id);
    }
}
