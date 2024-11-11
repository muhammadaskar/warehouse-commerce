package com.ecommerce.app.common.domain.valueobject;

import java.util.UUID;

public class UserId extends BaseId<UUID> {
    public UserId(UUID id) {
        super(id);
    }
}
