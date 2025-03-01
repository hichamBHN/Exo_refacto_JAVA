package com.nimbleways.springboilerplate.services.strategy;


import com.nimbleways.springboilerplate.entities.Product;

public interface ProductHandlerStrategy {
    void handle(Product product);
}
