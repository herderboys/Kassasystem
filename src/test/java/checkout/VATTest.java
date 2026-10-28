package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VATTest {
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
