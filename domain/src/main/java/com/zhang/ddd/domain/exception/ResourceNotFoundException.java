package com.zhang.ddd.domain.exception;

public class ResourceNotFoundException extends DomainBaseException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
