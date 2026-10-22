package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void productHasName() {
        Product product = new Product("Product");
        assertEquals("Product", product.getName());
    }
}
