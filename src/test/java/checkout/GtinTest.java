package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void gtinWithDashesIsNormalized() {
        Gtin gtin = new Gtin("400-6381-333931");
        assertEquals("4006381333931", gtin.getCode());
    }

    @Test
    void gtinWithSpacesAtEndsAndInARowIsNormalized() {
        Gtin gtin = new Gtin("  400  6381 333931 ");
        assertEquals("4006381333931", gtin.getCode());
    }

    @Test
    void gtinWithTabAndNewlineIsNormalized() {
        Gtin gtin = new Gtin("400\t6381\n333931");
        assertEquals("4006381333931", gtin.getCode());
    }

    @Test
    void gtinWithLeadingZerosKeepsZeros() {
        Gtin gtin = new Gtin("0012345678905");
        assertEquals("0012345678905", gtin.getCode());
    }

    @Test
    void gtinWithCheckDigitZeroIsAccepted() {
        Gtin gtin = new Gtin("5006381333930");
        assertEquals("5006381333930", gtin.getCode());
    }

    @Test
    void gtinWithCheckDigitNineIsAccepted() {
        Gtin gtin = new Gtin("6006381333939");
        assertEquals("6006381333939", gtin.getCode());
    }

    @Test
    void nullGtinIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin(null));
    }

    @Test
    void blankGtinIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("  "));
    }

    @Test
    void gtinWithCharacterOtherThanNumberIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("40063813339a1"));
    }

    @Test 
    void gtinWithSymbolIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("4006381.33931"));
    }

    @Test 
    void gtinWithCharacterBeforeZeroIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("4006381/33931"));
    }

    @Test 
    void gtinWithCharacterAfterNineIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("4006381:33931"));
    }

    @Test 
    void gtinWithNonAsciiDigitIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("٤006381333931"));
    }

    @Test 
    void gtinWithOneDigitIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("4"));
    }

    @Test
    void gtinWithLessThanThirteenDigitsIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("400638133393"));
    }

    @Test
    void gtinWithMoreThanThirteenDigitsIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("40063813339310"));
    }

    @Test
    void gtinWithWrongCheckDigitIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("4006381333939"));
    }

    @Test
    void gtinWithWrongDigitInMiddleIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("4006381433931"));
    }

    @Test 
    void gtinWithSwappedDigitsIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Gtin("4006383133931"));
    }

    @Test
    void twoGtinWithDifferentFormatsAreEqual() {
        Gtin gtin1 = new Gtin("4006381333931");
        Gtin gtin2 = new Gtin("400 6381-333931");
        assertEquals(gtin1, gtin2);
    }

    @Test
    void gtinIsNotEqualToNull() {
        Gtin gtin = new Gtin("4006381333931");
        assertFalse(gtin.equals(null));
    }
}
