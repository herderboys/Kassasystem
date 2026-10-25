package checkout;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class DiscountTest {
	
	private PercentageDiscount createPercentageDiscount(String name, int discountNumber) {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");

	    Clock clock = Clock.fixed(Instant.parse("2026-10-23T10:00:00Z"),zone);

	    return new PercentageDiscount(
	        name,
	        discountNumber,
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        clock
	    );
	}
	
	private MultiBuyDiscount createMultiBuyDiscount(String name, int quantityRequired,int quantityToPay) {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");

	    Clock clock = Clock.fixed(Instant.parse("2026-10-23T10:00:00Z"),zone);

	    return new MultiBuyDiscount(
	        name,
	        quantityRequired,
	        quantityToPay,
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        clock
	    );
	}
	

	@Test
	void testGetNamn() {
		PercentageDiscount discount = createPercentageDiscount("Discount1", 10);
        assertEquals("Discount1", discount.getName());
	}
	
	@Test
	void testGetDiscountNumber() {
		PercentageDiscount discount = createPercentageDiscount("Discount1", 10);
        assertEquals(10, discount.getDiscountPercentage());
	}
	@Test
	void testNotAcceptNegativeDiscount() {
		assertThrows(
		        IllegalArgumentException.class,
		        () -> createPercentageDiscount("Discount1", -10));
	}
	@Test
	void testPercentileDiscountCantBeOver100() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createPercentageDiscount("Discount1", 101)
	    );
	}
	
	@Test
	void testAccepts100PercentDiscount() {
	    PercentageDiscount discount =
	    		createPercentageDiscount("Discount1", 100);

	    assertEquals(100, discount.getDiscountPercentage());
	}

	@Test
	void testRejectsZeroPercentDiscount() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createPercentageDiscount("Discount1", 0)
	    );
	}
	
	@Test
    void testMultiBuyReturnQuantityRequired() {
		MultiBuyDiscount discount = createMultiBuyDiscount("3 for 2", 3, 2);
		
		assertEquals(3, discount.getQuantityRequired());
		
	}
	
	@Test
    void testMultiBuyReturnQuantityToPay() {
		MultiBuyDiscount discount = createMultiBuyDiscount("3 for 2", 3, 2);
		
		assertEquals(2, discount.getQuantityToPay());
		
	}
	
	@Test
    void testMultiBuyReturnQuantityLessThanQuantityToPay() {
		
		assertThrows(
		        IllegalArgumentException.class,
		        () -> createMultiBuyDiscount("3 for 2", 2, 3)
		    );
		
	}
	
	
	@Test
    void testDiscountIsValidDuringValidityPeriod() {
        ZoneId zone = ZoneId.of("Europe/Stockholm");

        Clock clock = Clock.fixed(
            Instant.parse("2026-10-23T10:00:00Z"),
            zone
        );

        PercentageDiscount discount = createPercentageDiscount(
            "Sommarrea", 10);

        assertTrue(discount.isValid());
    }

}
	
	

