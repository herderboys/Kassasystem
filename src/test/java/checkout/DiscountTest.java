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
	void testDiscountRejectsNullName() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createPercentageDiscount(null, 10)
	    );
	}
	
	@Test
	void testMemberDiscountHas20PercentDiscount() {
	    MemberDiscount discount = new MemberDiscount(
	        "Member discount",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        Clock.fixed(
	            Instant.parse("2026-10-23T10:00:00Z"),
	            ZoneId.of("Europe/Stockholm")
	        )
	    );

	    assertEquals(20, discount.getDiscountPercentage());
	}

	@Test
	void testMemberDiscountRejectsNullName() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new MemberDiscount(
	            null,
	            LocalDateTime.of(2026, 10, 23, 11, 0),
	            LocalDateTime.of(2029, 10, 23, 13, 0),
	            Clock.systemDefaultZone()
	        )
	    );
	}
	
	@Test
	void testDiscountRejectsNullClock1() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new PercentageDiscount(
	            "Discount1",
	            10,
	            LocalDateTime.of(2026, 10, 23, 11, 0),
	            LocalDateTime.of(2029, 10, 23, 13, 0),
	            null
	        )
	    );
	}
	
	@Test
	void testStudentSeniorDiscountHas10PercentDiscount() {
	    StudentSeniorDiscount discount = new StudentSeniorDiscount(
	        "Student discount",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        Clock.fixed(
	            Instant.parse("2026-10-23T10:00:00Z"),
	            ZoneId.of("Europe/Stockholm")
	        )
	    );

	    assertEquals(10, discount.getDiscountPercentage());
	}

	@Test
	void testStudentSeniorDiscountRejectsNullName() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new StudentSeniorDiscount(
	            null,
	            LocalDateTime.of(2026, 10, 23, 11, 0),
	            LocalDateTime.of(2029, 10, 23, 13, 0),
	            Clock.systemDefaultZone()
	        )
	    );
	}
	
	@Test
	void testDiscountRejectsNullClock() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new PercentageDiscount(
	            "Discount1",
	            10,
	            LocalDateTime.of(2026, 10, 23, 11, 0),
	            LocalDateTime.of(2029, 10, 23, 13, 0),
	            null
	        )
	    );
	}
	
	@Test
	void testDiscountRejectsBlankName() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createPercentageDiscount("   ", 10)
	    );
	}
	
	
	@Test
	void testGetName() {
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
    void testMultiBuyRejectsQuantityRequiredLessThanQuantityToPay() {
		
		assertThrows(
		        IllegalArgumentException.class,
		        () -> createMultiBuyDiscount("3 for 2", 2, 3)
		    );
		
	}
	
	
	@Test
	void testDiscountIsValidDuringValidityPeriod() {
	    PercentageDiscount discount =
	        createPercentageDiscount("Sommarrea", 10);

	    assertTrue(discount.isValid());
	}
	
	@Test
	void testMultiBuyRejectsEqualQuantities() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createMultiBuyDiscount("2 for 2", 2, 2)
	    );
	}
	
	@Test
	void testMultiBuyRejectsZeroQuantityToPay() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createMultiBuyDiscount("3 for 0", 3, 0)
	    );
	}
	
	@Test
	void testMultiBuyRejectsNegativeQuantityRequired() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createMultiBuyDiscount("Invalid", -1, 1)
	    );
	}

	@Test
	void testMultiBuyRejectsNegativeQuantityToPay() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createMultiBuyDiscount("Invalid", 3, -1)
	    );
	}
	
	@Test
	void testMultiBuyRejectsQuantityRequiredOfOneWhenQuantityToPayIsOne() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> createMultiBuyDiscount("1 for 1", 1, 1)
	    );
	}
	
	@Test
	void testMultiBuyAcceptsMinimumValidQuantities() {
	    MultiBuyDiscount discount =
	        createMultiBuyDiscount("2 for 1", 2, 1);

	    assertEquals(2, discount.getQuantityRequired());
	    assertEquals(1, discount.getQuantityToPay());
	}
	
	@Test
	void testDiscountRejectsNullEndTime() {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");
	    Clock clock = Clock.system(zone);

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new PercentageDiscount(
	            "Discount1",
	            10,
	            LocalDateTime.of(2026, 10, 23, 11, 0),
	            null,
	            clock
	        )
	    );
	}
	
	@Test
	void testDiscountRejectsEndDateBeforeStartDate() {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");
	    Clock clock = Clock.fixed(
	        Instant.parse("2026-10-23T10:00:00Z"),
	        zone
	    );

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new PercentageDiscount(
	            "Discount1",
	            10,
	            LocalDateTime.of(2027, 1, 1, 12, 0),
	            LocalDateTime.of(2026, 1, 1, 12, 0),
	            clock
	        )
	    );
	}
	
	@Test
	void testDiscountIsNotValidAfterEndDate() {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");
	    Clock clock = Clock.fixed(
	        Instant.parse("2026-10-23T10:00:00Z"),
	        zone
	    );

	    PercentageDiscount discount = new PercentageDiscount(
	        "Discount1",
	        10,
	        LocalDateTime.of(2026, 10, 1, 0, 0),
	        LocalDateTime.of(2026, 10, 20, 23, 59),
	        clock
	    );

	    assertFalse(discount.isValid());
	}
	
	@Test
	void testDiscountIsNotValidBeforeStartDate() {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");

	    Clock clock = Clock.fixed(
	        Instant.parse("2026-10-23T08:00:00Z"),
	        zone
	    );

	    PercentageDiscount discount = new PercentageDiscount(
	        "Discount1",
	        10,
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        clock
	    );

	    assertFalse(discount.isValid());
	}
	
	@Test
	void testDiscountRejectsNullStartTime() {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");
	    Clock clock = Clock.system(zone);

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new PercentageDiscount(
	            "Discount1",
	            10,
	            null,
	            LocalDateTime.of(2029, 10, 23, 13, 0),
	            clock
	        )
	    );
	}
	
	@Test
	void testDiscountIsValidExactlyAtStartTime() {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");

	    LocalDateTime startTime = LocalDateTime.of(2026, 10, 23, 12, 0);
	    LocalDateTime endTime = LocalDateTime.of(2026, 10, 23, 13, 0);

	    Clock clock = Clock.fixed(
	        startTime.atZone(zone).toInstant(),
	        zone
	    );

	    PercentageDiscount discount = new PercentageDiscount(
	        "Discount1",
	        10,
	        startTime,
	        endTime,
	        clock
	    );

	    assertTrue(discount.isValid());
	}
	
	@Test
	void testDiscountIsValidExactlyAtEndTime() {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");

	    LocalDateTime startTime = LocalDateTime.of(2026, 10, 23, 11, 0);
	    LocalDateTime endTime = LocalDateTime.of(2026, 10, 23, 12, 0);

	    Clock clock = Clock.fixed(
	        endTime.atZone(zone).toInstant(),
	        zone
	    );

	    PercentageDiscount discount = new PercentageDiscount(
	        "Discount1",
	        10,
	        startTime,
	        endTime,
	        clock
	    );

	    assertTrue(discount.isValid());
	}

}
	
	

