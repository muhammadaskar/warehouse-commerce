package com.ecommerce.app.order.application.exception.handler;

import com.ecommerce.app.common.application.handler.ErrorDTO;
import com.ecommerce.app.common.application.handler.GlobalExceptionHandler;
import com.ecommerce.app.order.domain.core.exception.OrderException;
import com.ecommerce.app.order.domain.core.exception.OrderNotFoundException;
import com.ecommerce.app.order.domain.core.exception.UserForbidden;
import com.ecommerce.app.order.domain.core.exception.UserUnauthorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class OrderExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = OrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(OrderException exception) {
        log.error("OrderException: ", exception);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {OrderNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(OrderNotFoundException orderNotFound) {
        log.error(orderNotFound.getMessage(), orderNotFound);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(orderNotFound.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {UserUnauthorized.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleException(UserUnauthorized userUnauthorized) {
        log.error(userUnauthorized.getMessage(), userUnauthorized);
        return ErrorDTO.builder()
                .code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(userUnauthorized.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {UserForbidden.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleException(UserForbidden userForbidden) {
        log.error(userForbidden.getMessage(), userForbidden);
        return ErrorDTO.builder()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(userForbidden.getMessage())
                .build();
    }
}
