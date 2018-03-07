package com.tesina.smop_smartshop;

/**
 * Created by matte on 07/03/2018.
 */

class Product {
    String productName, branding, ingredients, description, barcode;
    int quantity;
    double price, discount;

    public Product(String productName, String branding, String ingredients, String description, String barcode, int quantity, double price, double discount) {
        this.productName = productName;
        this.branding = branding;
        this.ingredients = ingredients;
        this.description = description;
        this.barcode = barcode;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }
}
