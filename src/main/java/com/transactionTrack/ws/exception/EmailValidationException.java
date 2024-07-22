package com.transactionTrack.ws.exception;

public class EmailValidationException extends RuntimeException {
    private String message;

    public EmailValidationException(String message){
        super(message);
    }

}
