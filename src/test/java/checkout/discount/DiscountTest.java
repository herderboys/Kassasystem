package checkout.discount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import checkout.product.ProductGrupp;

class DiscountTest {

	@Test
	void testGetNamn() {
		Discount discount = new Discount("Discount1");
        assertEquals("Discount1", discount.getName());
	}

}
