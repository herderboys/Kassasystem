package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class VatCategoryTest {
    @Test
    void vatCategoryReturnsItsVatRate() {
        VatCategory category = new VatCategory(25);
        assertEquals(25, category.getVatRate());
    }
}
