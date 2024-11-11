package com.ecommerce.app.common.domain.valueobject;

import java.util.UUID;

public class InventoryId extends BaseId<UUID>{
    public InventoryId(UUID id) {
        super(id);
    }
}
