package tesina.smop_smart_shop.Products;

/**
 * Created by matte on 22/02/2018.
 */

public class ProductGroup {
    private String productName;
    private double price, discount;
    private int quantity;

    public ProductGroup(String productName, double price, int quantity, double discount) {
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
