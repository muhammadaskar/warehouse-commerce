package com.ecommerce.app.application.exception.handler;

import com.ecommerce.app.common.application.handler.ErrorDTO;
import com.ecommerce.app.common.application.handler.GlobalExceptionHandler;
import com.ecommerce.app.user.domain.core.exception.UserException;
import com.ecommerce.app.user.domain.core.exception.UserForbidden;
import com.ecommerce.app.user.domain.core.exception.UserNotFoundException;
import com.ecommerce.app.user.domain.core.exception.UserUnauthorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class UserGlobalExceptionHandler extends GlobalExceptionHandler {
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
    @ExceptionHandler(value = {UserException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(UserException userException) {
        log.error(userException.getMessage(), userException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(userException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(UserNotFoundException userNotFoundException) {
        log.error(userNotFoundException.getMessage(), userNotFoundException);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(userNotFoundException.getMessage())
                .build();
    }
}
