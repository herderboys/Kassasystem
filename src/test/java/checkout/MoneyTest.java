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
        Money originalMoney = new Money(500);
        Money returnMoney = originalMoney.add(new Money(200));

        assertEquals(500, originalMoney.getAmountInOre());
        assertEquals(700, returnMoney.getAmountInOre());
    }

    @Test
    void ensureSubtractionReturnsRightResult() {
        Money originalMoney = new Money(500);
        Money returnMoney = originalMoney.subtract(new Money(200));

        assertEquals(500, originalMoney.getAmountInOre());
        assertEquals(300, returnMoney.getAmountInOre());
    }

    @Test
    void canSubtractToZero() {
        Money money = new Money(300);
        money = money.subtract(new Money(300));

        assertEquals(0, money.getAmountInOre());
    }

    @Test 
    void cannotSubtractBelowZero() {
        Money money = new Money(300);
        assertThrows(IllegalArgumentException.class, () -> money.subtract(new Money(301)));
    }
}
