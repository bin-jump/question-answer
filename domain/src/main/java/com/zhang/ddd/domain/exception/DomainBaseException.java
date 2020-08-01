package com.zhang.ddd.domain.exception;

public class DomainBaseException extends RuntimeException {

    public DomainBaseException() {

    }

    public DomainBaseException(String message) {
        super(message);
    }
}
