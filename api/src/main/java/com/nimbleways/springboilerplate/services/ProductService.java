package com.nimbleways.springboilerplate.services;

import com.nimbleways.springboilerplate.entities.Product;

public interface ProductService {
    void handleNormalProduct(Product p);
    void notifyDelay(int leadTime, Product p);
    void handleSeasonalProduct(Product p);
    void handleExpiredProduct(Product p);
    void save(Product p);
}
