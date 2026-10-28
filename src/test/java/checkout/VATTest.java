package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VATTest {
    @Test
    void vat25Returns25Percent() {
        VAT vat = new VAT25();
        assertEquals(25, vat.getVatRate());
    }
}
