package checkout;

import java.util.HashMap;
import java.util.Map;

public class ProductRegister {
    Map<Gtin, Product> items = new HashMap<>();

    void addProduct(Gtin gtin, Product product) {
        items.put(gtin, product);
    }

    public Product getProduct(Gtin gtin) {
        return items.get(gtin);
    }
}
