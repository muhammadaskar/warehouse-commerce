package com.ecommerce.app.warehouse.domain.core.exception;

import com.ecommerce.app.common.domain.exception.DomainException;

public class WarehouseNotFoundException extends DomainException {
    public WarehouseNotFoundException(String message) {
        super(message);
    }

    public WarehouseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
