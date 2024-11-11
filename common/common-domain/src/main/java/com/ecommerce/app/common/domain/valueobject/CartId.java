package com.ecommerce.app.common.domain.valueobject;

import java.util.UUID;

public class CartId extends BaseId<UUID> {

    public CartId(UUID value) {
        super(value);
    }
}
