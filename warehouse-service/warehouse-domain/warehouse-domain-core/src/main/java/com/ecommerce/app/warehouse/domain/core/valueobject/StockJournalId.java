package com.ecommerce.app.warehouse.domain.core.valueobject;

import com.ecommerce.app.common.domain.valueobject.BaseId;

import java.util.UUID;

public class StockJournalId extends BaseId<UUID> {
    public StockJournalId(UUID value) {
        super(value);
    }
}
