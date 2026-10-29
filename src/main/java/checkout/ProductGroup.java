
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

        // En produkt får bara tillhöra en produktgrupp.
        if (product.getProductGroup() != null
                && product.getProductGroup() != this) {
            throw new IllegalArgumentException(
                "Product already belongs to another group"
            );
        }

        // Undvik att lägga till samma produkt flera gånger.
        if (products.contains(product)) {
            return;
        }

        products.add(product);
        product.setProductGroup(this);
    }

    public boolean removeProduct(Product product) {
        if (product == null || !products.remove(product)) {
            return false;
        }

        // Synkronisera produktens referens med gruppens lista.
        product.setProductGroup(null);
        return true;
    }

    public boolean removeDiscount(Discount discount) {
        if (discount == null) {
            return false;
        }

        return discounts.remove(discount);
    }

    public void addDiscount(Discount discount) {
        if (discount == null) {
            throw new IllegalArgumentException(
                "Discount cannot be null"
            );
        }

        // Medlems- och student-/seniorrabatter får bara
        // förekomma en gång per produktgrupp.
        boolean duplicateUniqueType = isUniqueDiscountType(discount)
            && discounts.stream().anyMatch(
                existing -> existing.getClass() == discount.getClass()
            );

        if (duplicateUniqueType) {
            throw new IllegalArgumentException(
                "This discount type already exists in the product group"
            );
        }

        discounts.add(discount);
    }

    private boolean isUniqueDiscountType(Discount discount) {
        return discount instanceof MemberDiscount
            || discount instanceof StudentSeniorDiscount;
    }

    public List<Discount> getDiscounts() {
        return new ArrayList<>(discounts);
    }
}