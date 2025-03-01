package com.nimbleways.springboilerplate.services;

import com.nimbleways.springboilerplate.entities.Order;

public interface OrderService {
    Order findById(Long id);
    Long processOrder(Long id);
    Long processOrderUsingStrategy(Long id);
}
