package com.ecommerce.app.user.domain.core.valueobject;

import com.ecommerce.app.common.domain.valueobject.BaseId;

import java.util.UUID;

public class AddressId extends BaseId<UUID> {
    public AddressId(UUID id) {
        super(id);
    }
}
