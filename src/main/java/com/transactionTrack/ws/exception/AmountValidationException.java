package com.transactionTrack.ws.exception;

public class AmountValidationException extends RuntimeException {

    private String message;

    public AmountValidationException(String message){
        super(message);
    }

}
