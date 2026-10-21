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

    @Test
    void ensureAdditionReturnsRightResult() {
        Money money = new Money(500);
        Money returnMoney = money.add(new Money(200));

        assertEquals(500, money.getAmountInOre());
        assertEquals(700, returnMoney.getAmountInOre());
    }

    @Test
    void ensureSubtractionReturnsRightResult() {
        Money originalMoney = new Money(500);
        Money returnMoney = originalMoney.subtract(200);

        assertEquals(300, returnMoney.getAmountInOre());
    }

}
