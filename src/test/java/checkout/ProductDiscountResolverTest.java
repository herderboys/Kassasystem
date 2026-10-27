package checkout;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.lang.foreign.GroupLayout;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

class ProductDiscountResolverTest {

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
	
	private FixedDiscount createFixedDiscount(String name, Money discountAmount) {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");

	    Clock clock = Clock.fixed(Instant.parse("2026-10-23T10:00:00Z"),zone);

	    return new FixedDiscount(
	        name,
	        discountAmount,
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        clock
	    );
	}
	
    @Test
    void testMultiBuyDiscountReturn() {
    	
    	ProductDiscountResolver resolver = new ProductDiscountResolver();
    	
    	Product product = new Product("Milk",new Money(20));
    	
    	PercentageDiscount discount1 = createPercentageDiscount("50of", 50);
    	
    	MultiBuyDiscount discount2 = createMultiBuyDiscount("3x2", 3 , 2);
    	
    	ProductGroup group = new ProductGroup("group");
    	
    	group.addProduct(product);
    	
    	group.addDiscount(discount1);
    	
    	group.addDiscount(discount2);
    	
    	Money newPriceMoney = resolver.getNewPrice();
    	
    	assertEquals(new Money(10), newPrice); 
    }
}






