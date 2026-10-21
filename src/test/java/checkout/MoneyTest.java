package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    void moneyReturnsAmountItWasInitializedWith() {
        Money money = new Money(200);
        assertEquals(200, money.getAmountInOre());
    }

    @Test
    void ensureNoNegativeInitialValues() {
        assertThrows(IllegalArgumentException.class, () -> new Money(-1));
    }
}
