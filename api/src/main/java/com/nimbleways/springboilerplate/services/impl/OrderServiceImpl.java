package com.nimbleways.springboilerplate.services.impl;

import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.exceptions.OrderNotFoundException;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.services.OrderService;
import com.nimbleways.springboilerplate.services.strategy.ProductHandlerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final  ProductServiceImpl productService;
    private final ProductHandlerFactory productHandlerFactory;

    @Override
    public Order findById(Long id) {
        log.info("Finding order by id {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found by id {}", id);
                    return new OrderNotFoundException(id);
                });
    }

    @Override
    public Long processOrder(Long orderId) {
        log.info("Processing order {}", orderId);
        Order order = findById(orderId);
        Set<Product> products = order.getItems();
        for (Product p : products) {
            switch (p.getType()) {
                // we can use enum
                case "NORMAL" -> productService.handleNormalProduct(p);
                case "SEASONAL" -> productService.handleSeasonalProduct(p);
                case "EXPIRABLE" -> productService.handleExpiredProduct(p);
            }
        }
        log.info("Order {} processed", orderId);
        return order.getId();

    }

    @Override
    public Long processOrderUsingStrategy(Long id) {
        log.info("Processing order {} using strategy method", id);
        Order order = findById(id);
        for (Product product : order.getItems()) {
            productHandlerFactory.getStrategy(product.getType()).handle(product);
        }
        log.info("Order {} processed using strategy method", id);

        return order.getId();
    }


}
