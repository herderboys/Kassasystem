package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CheckoutTest {

    @Mock 
    Scanner scanner;

    @Test 
    void scannedItemReturnsProduct() {
        Product milk = new Product("Milk", new Money(200));
        Gtin gtin = new Gtin("4006381333931");
        ProductRegister register = new ProductRegister();
        Checkout checkout = new Checkout(scanner, register);

        register.addProduct(gtin, milk);

        when(scanner.scan()).thenReturn(gtin);

        assertEquals(milk, checkout.scanItem()); 
    }
}
