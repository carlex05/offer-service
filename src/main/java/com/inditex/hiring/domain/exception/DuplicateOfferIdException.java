package com.inditex.hiring.domain.exception;

public class DuplicateOfferIdException extends RuntimeException {
    public DuplicateOfferIdException(String message) {
        super(message);
    }
}