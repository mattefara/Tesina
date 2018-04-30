package tesina.smop_smart_shop.Products;

/**
 * Created by matte on 22/02/2018.
 */

public class ProductDetails {
    String branding, ingredients, description, barCode;

    public ProductDetails(String branding, String ingredients, String description, String barCode) {
        this.branding = branding;
        this.ingredients = ingredients;
        this.description = description;
        this.barCode = barCode;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
