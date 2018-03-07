package com.tesina.smop_smartshop;

/**
 * Created by matte on 22/02/2018.
 */

public class ProductGroup {
    private String productName;
    private double price;

    public ProductGroup(String productName, double price) {
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
