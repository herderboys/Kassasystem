
package checkout;

import java.util.ArrayList;
import java.util.List;

public class ProductGroup {

    private final String name;
    private final List<Product> products = new ArrayList<>();
    private final List<Discount> discounts = new ArrayList<>();

    public ProductGroup(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Name cannot be blank"
            );
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException(
                "Product cannot be null"
            );
        }
        if (product.getProductGroup() != null
                && product.getProductGroup() != this) {
            throw new IllegalArgumentException(
                "Product already belongs to another group");
        }

        if (products.contains(product)) {
            return;
        }

        products.add(product);
        product.setProductGroup(this);
    }

    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    public void removeDiscount(Discount discount) {
       
        discounts.remove(discount);
    }
    
    public void addDiscount(Discount discount) {
       
         discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return new ArrayList<>(discounts);
    }
}