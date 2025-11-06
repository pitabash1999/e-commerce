package com.eCcommerce.main.exception;

public class ExcessPaymentException extends RuntimeException{
    public ExcessPaymentException(String message){
        super(message);
    }
}
