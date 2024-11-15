package com.ecommerce.app.user.domain.core.exception;

import com.ecommerce.app.common.domain.exception.DomainException;

public class UserException extends DomainException {
    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
