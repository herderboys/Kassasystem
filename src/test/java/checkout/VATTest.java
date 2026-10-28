package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class VATTest {
    @Test
    void priceExcludingVatCannotBeNull() {
        VAT vat = new VAT25();
        assertThrows(IllegalArgumentException.class, () -> vat.calculateVatFromNetPrice(null));
    }

    @Test
    void calculatesVatFromPriceExcludingVat() {
        VAT vat = new VAT25();
        Money priceExcludingVat = new Money(10000);

        assertEquals(new Money(2500), vat.calculateVatFromNetPrice(priceExcludingVat));
    }

    @Test
    void vat0Returns0Percent() {
        VAT vat = new VAT0();
        assertEquals(0, vat.getVatRate());
    }

    @Test
    void vat6Returns6Percent() {
        VAT vat = new VAT6();
        assertEquals(6, vat.getVatRate());
    }

    @Test
    void vat12Returns12Percent() {
        VAT vat = new VAT12();
        assertEquals(12, vat.getVatRate());
    }

    @Test
    void vat25Returns25Percent() {
        VAT vat = new VAT25();
        assertEquals(25, vat.getVatRate());
    }
}
