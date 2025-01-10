package com.ecommerce.app.warehouse.application.exception;

import com.ecommerce.app.common.application.handler.ErrorDTO;
import com.ecommerce.app.common.application.handler.GlobalExceptionHandler;
import com.ecommerce.app.warehouse.domain.core.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class WarehouseGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {WarehouseDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(WarehouseDomainException warehouseException) {
        log.error(warehouseException.getMessage(), warehouseException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(warehouseException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {WarehouseNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(WarehouseNotFoundException warehouseNotFound) {
        log.error(warehouseNotFound.getMessage(), warehouseNotFound);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(warehouseNotFound.getMessage())
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

    @ResponseBody
    @ExceptionHandler(value = {ProductNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(ProductNotFoundException productNotFound) {
        log.error(productNotFound.getMessage(), productNotFound);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(productNotFound.getMessage())
                .build();
    }
}
