package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
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

    @Test
    void scannedGtinIsPassedOnToRegister() {
        ProductRegister mockRegister = mock(ProductRegister.class);
        Checkout checkoutWithMockRegister = new Checkout(scanner, mockRegister);
        when(scanner.scan()).thenReturn(gtin);
        when(mockRegister.getProduct(any(Gtin.class))).thenReturn(milk);

        checkoutWithMockRegister.scanItem();

        ArgumentCaptor<Gtin> captor = ArgumentCaptor.forClass(Gtin.class);
        verify(mockRegister).getProduct(captor.capture());
        assertEquals("4006381333931", captor.getValue().getCode());
    }

    @Test
    void scannerIsReadBeforeRegisterIsAsked() {
        ProductRegister mockRegister = mock(ProductRegister.class);
        Checkout checkoutWithMockRegister = new Checkout(scanner, mockRegister);
        when(scanner.scan()).thenReturn(gtin);
        when(mockRegister.getProduct(gtin)).thenReturn(milk);

        checkoutWithMockRegister.scanItem();

        InOrder inOrder = inOrder(scanner, mockRegister);
        inOrder.verify(scanner).scan();
        inOrder.verify(mockRegister).getProduct(gtin);
    }

    @Test
    void spyUsesRealRegisterAndRecordsCalls() {
        ProductRegister spyRegister = spy(new ProductRegister());
        spyRegister.addProduct(gtin, milk);
        Checkout checkoutWithSpy = new Checkout(scanner, spyRegister);
        when(scanner.scan()).thenReturn(gtin);

        assertEquals(milk, checkoutWithSpy.scanItem());
        verify(spyRegister).getProduct(gtin);
    }

    @Test
    void scannerErrorIsPassedOnToCaller() {
        when(scanner.scan()).thenThrow(new IllegalStateException("Scanner error"));

        assertThrows(IllegalStateException.class, () -> checkout.scanItem());
    }
}
