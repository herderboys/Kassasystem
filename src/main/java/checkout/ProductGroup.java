package checkout;

import java.util.ArrayList;
import java.util.List;

public class ProductGroup {

    private String name;
    private List<Object> products = new ArrayList<>();
    private List<Discount> discounts = new ArrayList<>();

    public ProductGroup(String name) {
    	if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
            }
    	this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Object> getProducts() {
        return new ArrayList<>(products);
    }

    public void addProduct(Object product) {
        this.products.add(product);
    }

    public boolean removeProduct(Object product) {
        return products.remove(product);
    }

    public void addDiscount(Discount discount) {
        this.discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return new ArrayList<>(discounts);
    }
}
