package com.nimbleways.springboilerplate.services.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductHandlerFactory {

    private final Map<String, ProductHandlerStrategy> strategyMap;

    public ProductHandlerFactory(
            NormalProductHandler normalProductHandler,
            SeasonalProductHandler seasonalProductHandler,
            ExpirableProductHandler expirableProductHandler) {
        // use enum product type
        this.strategyMap = Map.of(
                "NORMAL", normalProductHandler,
                "SEASONAL", seasonalProductHandler,
                "EXPIRABLE", expirableProductHandler
        );
    }

    public ProductHandlerStrategy getStrategy(String productType) {
        return strategyMap.getOrDefault(productType, p -> {
            throw new IllegalArgumentException("Unknown product type: " + productType);
        });
    }
}
