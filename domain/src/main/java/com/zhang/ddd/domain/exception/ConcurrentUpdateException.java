package com.zhang.ddd.domain.exception;

public class ConcurrentUpdateException extends DomainBaseException {

    public ConcurrentUpdateException(String message) {
        super(message);
    }
}
