package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void productHasName() {
        Product product = new Product("Product", new Money(10));
        assertEquals("Product", product.getName());
    }

    @Test 
    void productHasPrice() {
        Product product = new Product("Product", new Money(10));
        assertEquals(new Money(10), product.getPrice());
    }

    @Test 
    void productNameNotNull() {
        assertThrows(IllegalArgumentException.class, () -> new Product(null, new Money(500)));
    }
}
