package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class VatCategoryTest {
    @Test
    void calculatesVatFromSmallNetPrice() {
        VatCategory category = new VatCategory(25);
        assertEquals(new Money(25), category.calculateVatFromNetPrice(new Money(100)));
        assertEquals(new Money(0), category.calculateVatFromNetPrice(new Money(3)));
    }

    @Test
    void zeroVatRateGivesZeroVat() {
        VatCategory category = new VatCategory(0);
        assertEquals(new Money(0), category.calculateVatFromNetPrice(new Money(10000)));
    }

    @Test
    void priceExcludingVatCannotBeNull() {
        VatCategory category = new VatCategory(25);
        assertThrows(IllegalArgumentException.class, () -> category.calculateVatFromNetPrice(null));
    }

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

        VatCategory otherCategory = new VatCategory(12);
        assertEquals(new Money(1200), otherCategory.calculateVatFromNetPrice(priceExcludingVat));

        VatCategory sixPercentCategory = new VatCategory(6);
        assertEquals(new Money(600), sixPercentCategory.calculateVatFromNetPrice(priceExcludingVat));
    }
}
