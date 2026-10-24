
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
        products.add(product);
    }

    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    public void addDiscount(Discount discount) {
        if (discount == null) {
            throw new IllegalArgumentException(
                "Discount cannot be null"
            );
        }
        discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return new ArrayList<>(discounts);
    }
}