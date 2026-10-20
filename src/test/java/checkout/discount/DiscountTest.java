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
	
	@Test
	void testGetDiscountNumber() {
		Discount discount = new Discount("Discount1", 10.10);
        assertEquals(10.10, discount.getNumber());
	}
	
}
