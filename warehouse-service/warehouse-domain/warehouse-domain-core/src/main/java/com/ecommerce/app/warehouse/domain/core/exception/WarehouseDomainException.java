package com.ecommerce.app.warehouse.domain.core.exception;

import com.ecommerce.app.common.domain.exception.DomainException;

public class WarehouseDomainException extends DomainException {

    public WarehouseDomainException(String message) {
        super(message);
    }

    public WarehouseDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
