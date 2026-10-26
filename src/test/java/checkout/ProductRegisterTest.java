package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductRegisterTest {
    
    @Test 
    void registeredProductCanBeFoundByGtin() {
        Product milk = new Product("Milk", new Money(200));
        Gtin gtin = new Gtin("4006381333931");
        ProductRegister register = new ProductRegister();
        register.addProduct(gtin, milk);
        assertEquals(milk, register.getProduct(gtin));
    }
}
