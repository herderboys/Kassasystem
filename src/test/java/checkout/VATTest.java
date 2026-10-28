package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class VATTest {
    @Test
    void calculatesVatFromSmallNetPrice() {
        VAT vat = new VAT25();
        assertEquals(new Money(25), vat.calculateVatFromNetPrice(new Money(100)));
        assertEquals(new Money(0), vat.calculateVatFromNetPrice(new Money(3)));
    }

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

        VAT twelvePercentVat = new VAT12();
        assertEquals(new Money(1200), twelvePercentVat.calculateVatFromNetPrice(priceExcludingVat));

        VAT sixPercentVat = new VAT6();
        assertEquals(new Money(600), sixPercentVat.calculateVatFromNetPrice(priceExcludingVat));

        VAT zeroPercentVat = new VAT0();
        assertEquals(new Money(0), zeroPercentVat.calculateVatFromNetPrice(priceExcludingVat));
        assertEquals(new Money(0), zeroPercentVat.calculateVatFromNetPrice(new Money(100)));
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
