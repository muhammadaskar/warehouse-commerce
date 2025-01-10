package com.ecommerce.app.order.domain.core.valueobject;

import com.ecommerce.app.common.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<String> {
    public OrderItemId(String value) {
        super(value);
    }
}
