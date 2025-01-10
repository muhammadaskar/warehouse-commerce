package com.ecommerce.app.payment.application.exception.handler;

import com.ecommerce.app.common.application.handler.ErrorDTO;
import com.ecommerce.app.common.application.handler.GlobalExceptionHandler;
import com.ecommerce.app.payment.domain.core.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class PaymentGlobalExceptionHandler extends GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = {PaymentDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(PaymentDomainException paymentException) {
        log.error(paymentException.getMessage(), paymentException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(paymentException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {OrderDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(OrderDomainException orderException) {
        log.error(orderException.getMessage(), orderException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(orderException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {PaymentNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(PaymentNotFoundException productNotFound) {
        log.error(productNotFound.getMessage(), productNotFound);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(productNotFound.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message(exception.getMessage())
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

    @ResponseBody
    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(UserNotFoundException userNotFound) {
        log.error(userNotFound.getMessage(), userNotFound);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(userNotFound.getMessage())
                .build();
    }
}
