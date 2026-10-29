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

    @Test
    void productNameNotEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Product("", new Money(500)));
    }

    @Test 
    void productNameNotOnlyWhitespace() {
        assertThrows(IllegalArgumentException.class, () -> new Product("    ", new Money(500)));
    }

    @Test
    void productWithVATNameNotOnlyWhitespace() {
        assertThrows(IllegalArgumentException.class, () -> new Product("    ", new Money(500), new VAT25()));
    }

    @Test 
    void productMoneyNotNull() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Ingenting", null));
    }

    @Test
    void productWithVATMoneyNotNull() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Ingenting", null, new VAT25()));
    }

    @Test 
    void productMoneyCanBeZero() {
        Product product = new Product("Free", new Money(0));
        assertEquals(new Money(0), product.getPrice());
    }

}
