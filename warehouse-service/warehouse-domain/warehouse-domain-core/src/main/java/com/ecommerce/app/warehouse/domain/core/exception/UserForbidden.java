package com.ecommerce.app.warehouse.domain.core.exception;

import com.ecommerce.app.common.domain.exception.DomainException;

public class UserForbidden extends DomainException {
    public UserForbidden(String message) {
        super(message);
    }

    public UserForbidden(String message, Throwable cause) {
        super(message, cause);
    }
}
