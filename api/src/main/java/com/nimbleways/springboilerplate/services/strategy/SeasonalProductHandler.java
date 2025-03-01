package com.nimbleways.springboilerplate.services.strategy;


import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.services.ProductService;
import org.springframework.stereotype.Service;

@Service
public class SeasonalProductHandler implements ProductHandlerStrategy {

    private final ProductService productService;

    public SeasonalProductHandler(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void handle(Product product) {
        productService.handleSeasonalProduct(product);
    }
}
