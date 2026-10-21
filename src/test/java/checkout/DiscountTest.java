package checkout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class DiscountTest {

	@Test
	void testGetNamn() {
		Discount discount = new Discount("Discount1", 10);
        assertEquals("Discount1", discount.getName());
	}
	
	@Test
	void testGetDiscountNumber() {
		Discount discount = new Discount("Discount1", 10);
        assertEquals(10, discount.getDiscountNumber());
	}
	@Test
	void testNotAcceptNegativeDiscount() {
		assertThrows(
		        IllegalArgumentException.class,
		        () -> new Discount("Discount1", -10));
	}
	@Test
	void testPercentileDiscountCantBeOver100() {
		
	}
}
