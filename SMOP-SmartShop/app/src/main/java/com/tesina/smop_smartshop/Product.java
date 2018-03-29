package com.tesina.smop_smartshop;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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

    public String toString(){
        return this.productName + "," + this.branding + "," + this.ingredients + "," + this.description  + "," + this.barcode + "," + this.quantity + "," + this.price  + "," + this.discount + ";" ;
    }
}
