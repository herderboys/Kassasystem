package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class VatCategoryTest {
    @Test
    void vatRateCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new VatCategory(-1));
    }

    @Test
    void vatCategoryReturnsItsVatRate() {
        VatCategory category = new VatCategory(25);
        assertEquals(25, category.getVatRate());
    }

    @Test
    void calculatesVatFromPriceExcludingVat() {
        VatCategory category = new VatCategory(25);
        Money priceExcludingVat = new Money(10000);

        assertEquals(new Money(2500), category.calculateVatFromNetPrice(priceExcludingVat));
    }
}
