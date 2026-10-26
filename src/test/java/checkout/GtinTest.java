package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class GtinTest {

    @Test 
    void validGtinIsAccepted() {
        Gtin gtin = new Gtin("4006381333931");
        assertEquals("4006381333931", gtin.getCode());
    }

    @Test 
    void gtinWithSeparatorsIsNormalized() {
        Gtin gtin = new Gtin("400 6381-333931");
        assertEquals("4006381333931", gtin.getCode());
    }

    @Test 
    void nullGtinIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin(null));
    }

    @Test 
    void blankGtinIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("  "));
    }
}
