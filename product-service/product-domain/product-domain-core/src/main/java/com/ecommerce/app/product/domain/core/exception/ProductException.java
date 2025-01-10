package com.ecommerce.app.product.domain.core.exception;

import com.ecommerce.app.common.domain.exception.DomainException;

public class ProductException extends DomainException {
    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
