package com.ecommerce.app.order.domain.core.exception;

import com.ecommerce.app.common.domain.exception.DomainException;

public class OrderException extends DomainException {
    public OrderException(String message) {
        super(message);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
