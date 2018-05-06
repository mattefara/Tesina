package tesina.smop_smart_shop.Products;

public class DatabaseProduct {
    private String productName, branding, ingredients, description, barcode;
    private double price, discount;

    public DatabaseProduct(){}

    public DatabaseProduct(String productName, String branding, String ingredients, String description, String barcode, double price, double discount) {
        this.productName = productName;
        this.branding = branding;
        this.ingredients = ingredients;
        this.description = description;
        this.barcode = barcode;
        this.price = price;
        this.discount = discount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
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
}
