package com.nimbleways.springboilerplate.enums;

// we can use enum for define Product Type i's too practices
public enum ProductType {
    NORMAL("NORMAL"),
    SEASONAL("SEASONAL"),
    EXPIRABLE("EXPIRABLE"),;

    public final String value;

    ProductType(String value) {
        this.value = value;
    }

    public static  ProductType fromString(String value) {
        for (ProductType t : ProductType.values()) {
            if (t.value.equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid product type: " + value);
    }
}
