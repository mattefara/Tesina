package com.tesina.smop_app.Product;

public class DatabaseProduct extends UserProduct {

    private double price, discount;
    private int img;
    private String description, ingredients, brand, barcode;

    public DatabaseProduct(){
        super();
    }

    public DatabaseProduct(String name, int quantity, double price, double discount, int img, String description, String ingredients, String brand, String barcode) {
        super(name, quantity);
        this.price = price;
        this.discount = discount;
        this.img = img;
        this.description = description;
        this.ingredients = ingredients;
        this.brand = brand;
        this.barcode = barcode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return this.getName() + ","
                + this.getQuantity() + ","
                + this.getPrice() + ","
                + this.getDiscount() + ","
                + this.getImg() + ","
                + this.getDescription() + ","
                + this.getIngredients() + ","
                + this.getBrand() + ","
                + this.getBarcode() + ";";
    }
}
