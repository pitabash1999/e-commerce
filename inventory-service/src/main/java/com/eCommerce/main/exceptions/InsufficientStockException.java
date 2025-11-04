package com.eCommerce.main.exceptions;

public class InsufficientStockException extends Exception{

    public InsufficientStockException(String message){
        super(message);
    }
}
