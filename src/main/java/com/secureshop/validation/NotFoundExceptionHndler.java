package com.secureshop.validation;

public class NotFoundExceptionHndler extends RuntimeException{

    public NotFoundExceptionHndler(String message){
        super(message);
    }
}
