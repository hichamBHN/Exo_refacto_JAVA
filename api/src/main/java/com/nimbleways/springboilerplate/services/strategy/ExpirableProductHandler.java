package com.nimbleways.springboilerplate.services.strategy;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.services.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ExpirableProductHandler implements ProductHandlerStrategy {

    private final ProductService productService;

    public ExpirableProductHandler(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void handle(Product product) {
        productService.handleExpiredProduct(product);
    }
}
