package tesina.smop_smart_shop.Products;

public class ListProduct {
    // TODO:
    // add branding, ingredients, barcode
    private String productName, description, brand, ingredients, barcode;
    private int quantity, img;
    private double price, discount;
    // prevent user stealing some products. User pay only scanned product
    private int scannedQuantity = 0;
    private boolean isScanned;

    public ListProduct() {
    }


    public ListProduct(String productName, String description, String brand, String ingredients, String barcode, int quantity, int img, double price, double discount,int scannedQuantity, boolean isScanned) {
        this.productName = productName;
        this.description = checkConstructor( description);
        this.brand = checkConstructor(brand);
        this.ingredients = checkConstructor( ingredients);
        this.barcode = checkConstructor(barcode);
        this.quantity = quantity;
        this.img = img;
        this.price = price;
        this.discount = discount;
        this.scannedQuantity = scannedQuantity;
        this.isScanned = isScanned;
    }

    public ListProduct (DatabaseProduct databaseProduct, boolean isScanned){
        this.productName = databaseProduct.getProductName();
        this.description = databaseProduct.getDescription();
        this.brand = databaseProduct.getBranding();
        this.ingredients = databaseProduct.getIngredients();
        this.barcode = databaseProduct.getBarcode();
        this.price = databaseProduct.getPrice();
        this.discount = databaseProduct.getDiscount();
        this.isScanned = isScanned;
    }

    private String checkConstructor( String obj){
        return (obj.equals("") || obj == null) ? "" : obj;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getScannedQuantity() {
        return scannedQuantity;
    }

    public void setScannedQuantity(int scannedQuantity) {
        this.scannedQuantity = scannedQuantity;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    @Override
    public String toString() {
        return  productName +
                "," + description +
                "," + brand +
                "," + ingredients +
                "," + barcode +
                "," + quantity +
                "," + img +
                "," + price +
                "," + discount +
                "," + scannedQuantity +
                "," + isScanned + ";" ;
    }
}