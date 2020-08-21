package com.zhang.ddd.presentation.web.error;

import com.zhang.ddd.domain.exception.InvalidOperationException;
import com.zhang.ddd.domain.exception.InvalidValueException;
import com.zhang.ddd.infrastructure.common.api.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionTranslator {

    @ExceptionHandler(InvalidValueException.class)
    public Response handleDomainValueError(InvalidValueException e) {

        return Response.failed(e.getMessage());
    }

    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidOperationException.class)
    public Response handleDomainOperationError(InvalidOperationException e) {

        return Response.failed(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationError(MethodArgumentNotValidException
                                                           manve, HttpServletRequest request) {
        BindingResult result = manve.getBindingResult();
        List< FieldError > fieldErrors = result.getFieldErrors();
        FieldError er = fieldErrors.get(0);

        String message = fieldErrors.stream().map(e -> e.getField() + " is not valid, please input a correct one.\n")
                .reduce("", String::concat);

        return Response.failed(message);
    }

}
