package com.transactionTrack.ws.exception;

public class TransactionNotFoundException extends RuntimeException{

    private String message;

    public TransactionNotFoundException(String message){
        super(message);
    }

}
