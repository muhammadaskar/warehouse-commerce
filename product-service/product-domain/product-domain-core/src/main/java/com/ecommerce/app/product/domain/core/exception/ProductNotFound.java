package com.ecommerce.app.product.domain.core.exception;

import com.ecommerce.app.common.domain.exception.DomainException;

public class ProductNotFound extends DomainException {
    public ProductNotFound(String message) {
        super(message);
    }

    public ProductNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
