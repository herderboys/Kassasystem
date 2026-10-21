package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test 
    void moneyReturnsAmountItWasInitializedWith() {
        Money money = new Money(200);
        assertEquals(200, money.getAmountInOre());
    }
}
