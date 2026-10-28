package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CheckoutTest {

    @Mock
    Scanner scanner;

    private Product milk;
    private Gtin gtin;
    private ProductRegister register;
    private Checkout checkout;

    @BeforeEach
    void setup() {
        milk = new Product("Milk", new Money(200));
        gtin = new Gtin("4006381333931");
        register = new ProductRegister();
        checkout = new Checkout(scanner, register);
    }

    @Test
    void scannedItemReturnsProduct() {
        register.addProduct(gtin, milk);
        when(scanner.scan()).thenReturn(gtin);
        assertEquals(milk, checkout.scanItem());
    }

    @Test
    void scannedItemThatIsntInRegisterIsRejected() {
        when(scanner.scan()).thenReturn(gtin);
        assertThrows(NoSuchElementException.class, () -> checkout.scanItem());
    }

    @Test
    void scannedItemGetsScannedExactlyOnce() {
        register.addProduct(gtin, milk);
        when(scanner.scan()).thenReturn(gtin);
        checkout.scanItem();
        verify(scanner).scan();
    }
}
