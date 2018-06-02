package com.tesina.smop_app.Product;

public class UserProduct {
    private String name;
    private int quantity;
    private boolean isSelected, isEdited;

    public UserProduct(){}

    public UserProduct(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.isSelected = false;
        this.isEdited = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    @Override
    public String toString() {
        return this.getName() + "," + this.getQuantity() + ";";
    }
}
