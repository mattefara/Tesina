package com.tesina.smop_smartshop;

/**
 * Created by matte on 22/02/2018.
 */

public class ProductDetails {
    String branding, ingredients, description, barCode;
    double discount;
    int quantity;

    public ProductDetails(String branding, String ingredients, String description, String barCode, double discount, int quantity) {
        this.branding = branding;
        this.ingredients = ingredients;
        this.description = description;
        this.barCode = barCode;
        this.discount = discount;
        this.quantity = quantity;
    }

    public String getBranding() {
        return branding;
    }

    public void setBranding(String branding) {
        this.branding = branding;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
