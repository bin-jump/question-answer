package com.zhang.ddd.presentation.web.error;

import com.zhang.ddd.domain.exception.InvalidValueException;
import com.zhang.ddd.infrastructure.common.api.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionTranslator {

    @ExceptionHandler(InvalidValueException.class)
    public Response handleError(InvalidValueException e) {
        log.warn("Missing Request Parameter", e);
        return Response
                .builder()
                .status(Response.Status.FAILED)
                .msg(e.getMessage())
                .build();
    }
}
