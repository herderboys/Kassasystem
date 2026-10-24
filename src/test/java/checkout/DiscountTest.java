package checkout;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class DiscountTest {
	
	private PercentageDiscount createDiscount(String name, int discountNumber) {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");

	    Clock clock = Clock.fixed(
	        Instant.parse("2026-10-23T10:00:00Z"),
	        zone
	    );

	    return new PercentageDiscount(
	        name,
	        discountNumber,
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        clock
	    );
	}
	

	@Test
	void testGetNamn() {
		PercentageDiscount discount = createDiscount("Discount1", 10);
        assertEquals("Discount1", discount.getName());
	}
	
	@Test
	void testGetDiscountNumber() {
		PercentageDiscount discount = createDiscount("Discount1", 10);
        assertEquals(10, discount.getDiscountNumber());
	}
	@Test
	void testNotAcceptNegativeDiscount() {
		assertThrows(
		        IllegalArgumentException.class,
		        () -> createDiscount("Discount1", -10));
	}
	@Test
	void testPercentileDiscountCantBeOver100() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createDiscount("Discount1", 101)
	    );
	}
	
	@Test
	void testAccepts100PercentDiscount() {
	    PercentageDiscount discount =
	    		createDiscount("Discount1", 100);

	    assertEquals(100, discount.getDiscountNumber());
	}

	@Test
	void testRejectsZeroPercentDiscount() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createDiscount("Discount1", 0)
	    );
	}
	
	@Test
    void testDiscountIsValidDuringValidityPeriod() {
        ZoneId zone = ZoneId.of("Europe/Stockholm");

        Clock clock = Clock.fixed(
            Instant.parse("2026-10-23T10:00:00Z"),
            zone
        );

        PercentageDiscount discount = createDiscount(
            "Sommarrea", 10);

        assertTrue(discount.isValid());
    }

}
	
	

