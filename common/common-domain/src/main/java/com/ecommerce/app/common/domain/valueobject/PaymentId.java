package com.ecommerce.app.common.domain.valueobject;

import java.util.UUID;

public class PaymentId extends BaseId<UUID>{
    public PaymentId(UUID id) {
        super(id);
    }
}
