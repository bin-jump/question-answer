package com.zhang.ddd.domain.exception;

public class InvalidOperationException extends DomainBaseException {

    public InvalidOperationException(String message) {
        super(message);
    }
}