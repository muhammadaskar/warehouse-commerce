package com.ecommerce.app.product.domain.core.exception;

import com.ecommerce.app.common.domain.exception.DomainException;

public class UserUnauthorized extends DomainException {
    public UserUnauthorized(String message) {
        super(message);
    }

    public UserUnauthorized(String message, Throwable cause) {
        super(message, cause);
    }
}
