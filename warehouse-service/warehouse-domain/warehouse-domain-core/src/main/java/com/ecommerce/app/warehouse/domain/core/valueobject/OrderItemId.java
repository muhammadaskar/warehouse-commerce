package com.ecommerce.app.warehouse.domain.core.valueobject;

import com.ecommerce.app.common.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderItemId extends BaseId<UUID> {
    public OrderItemId(UUID value) {
        super(value);
    }
}
