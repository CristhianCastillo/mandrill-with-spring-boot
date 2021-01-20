package com.ptesa.demoemailmandril.exception;

public class EmailException extends Exception {

    public EmailException(String errorMessage) {
        super(errorMessage);
    }

    public EmailException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
