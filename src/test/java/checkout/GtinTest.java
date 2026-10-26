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
}
