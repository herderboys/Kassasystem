package checkout;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ProductRegister {
    Map<Gtin, Product> items = new HashMap<>();

    void addProduct(Gtin gtin, Product product) {
        items.put(gtin, product);
    }

    public Product getProduct(Gtin gtin) {
        Product product = items.get(gtin);

        if (product == null) {
            throw new NoSuchElementException(
                    "Item with GTIN " + gtin.getCode() + " does not exist.");
        }
        return product;
    }
}
