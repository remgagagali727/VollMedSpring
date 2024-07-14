package com.remproyects.voll.api.infra.exception;

public class IntegrityValidationException extends RuntimeException {
    public IntegrityValidationException(String s) {
        super(s);
    }
}
