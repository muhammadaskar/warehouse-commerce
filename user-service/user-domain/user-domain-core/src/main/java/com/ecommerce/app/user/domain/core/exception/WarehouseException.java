package com.ecommerce.app.user.domain.core.exception;

import com.ecommerce.app.common.domain.exception.DomainException;

public class WarehouseException extends DomainException {
    public WarehouseException(String message) {
        super(message);
    }

    public WarehouseException(String message, Throwable cause) {
        super(message, cause);
    }
}
