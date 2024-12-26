package com.ecommerce.app.user.domain.core.exception;

import org.w3c.dom.DOMException;

public class UserUnauthorized extends DOMException {
    public UserUnauthorized(String message) {
        super(DOMException.NOT_SUPPORTED_ERR, message);
    }
    public UserUnauthorized(String message, Throwable cause){
        super(DOMException.NOT_SUPPORTED_ERR, message);
    }
}
