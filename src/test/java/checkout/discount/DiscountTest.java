package checkout.discount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import checkout.product.ProductGrupp;

class DiscountTest {

	@Test
	void testGetNamn() {
		PercentageDiscount discount = new PercentageDiscount("Discount1", 10);
        assertEquals("Discount1", discount.getName());
	}
	
	@Test
	void testGetDiscountNumber() {
		PercentageDiscount discount = new PercentageDiscount("Discount1", 10);
        assertEquals(10, discount.getDiscountNumber());
	}
	@Test
	void testNotAcceptNegativeDiscount() {
		assertThrows(
		        IllegalArgumentException.class,
		        () -> new PercentageDiscount("Discount1", -10));
	}
	@Test
	void testPercentileDiscountCantBeOver100() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new PercentageDiscount("Discount1", 101)
	    );
	}
	
	@Test
	void testAccepts100PercentDiscount() {
	    PercentageDiscount discount =
	        new PercentageDiscount("Discount1", 100);

	    assertEquals(100, discount.getDiscountNumber());
	}

	@Test
	void testRejectsZeroPercentDiscount() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new PercentageDiscount("Discount1", 0)
	    );
	}
	
}
