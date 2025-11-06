package com.eCcommerce.main.exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message){
        super(message);
    }
}
