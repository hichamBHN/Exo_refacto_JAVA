package com.nimbleways.springboilerplate.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id){
        super("Order not found defined by id " + id);
    }
}
