package checkout;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
	
	private MemberDiscount createMemberDiscount(String name) {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");
	    Clock clock = Clock.fixed(
	        Instant.parse("2026-10-23T10:00:00Z"),
	        zone
	    );

	    return new MemberDiscount(
	        name,
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        clock
	    );
	}

	private StudentSeniorDiscount createStudentSeniorDiscount(String name) {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");
	    Clock clock = Clock.fixed(
	        Instant.parse("2026-10-23T10:00:00Z"),
	        zone
	    );

	    return new StudentSeniorDiscount(
	        name,
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        clock
	    );
	}
	
    @Test
    void percentageDiscountShouldReducePrice() {
    	
    	ProductDiscountResolver resolver = new ProductDiscountResolver();
    	
    	Product product = new Product("Milk",new Money(20));
    	
    	PercentageDiscount discount = createPercentageDiscount("50of", 50);
    	
    	ProductGroup group = new ProductGroup("group");
    	
    	group.addProduct(product);
    	
    	group.addDiscount(discount);
    	
    	
    	Money newPriceMoney = resolver.getNewPrice(product, 1);
    	
    	assertEquals(new Money(10), newPriceMoney); 
    }
    
    @Test
    void zeroQuantityShouldReturnZero() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));

        assertEquals(new Money(0), resolver.getNewPrice(product, 0));
    }

    @Test
    void negativeQuantityShouldThrowException() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));

        assertThrows(
            IllegalArgumentException.class,
            () -> resolver.getNewPrice(product, -1)
        );
    }

    @Test
    void productWithoutGroupShouldHaveNormalPrice() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));

        assertEquals(new Money(60), resolver.getNewPrice(product, 3));
    }

    @Test
    void fixedDiscountShouldReducePrice() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(
            createFixedDiscount("5 off", new Money(5))
        );

        assertEquals(new Money(15), resolver.getNewPrice(product, 1));
    }

    @Test
    void fixedDiscountShouldNotMakePriceNegative() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(
            createFixedDiscount("30 off", new Money(30))
        );

        assertEquals(new Money(0), resolver.getNewPrice(product, 1));
    }

    @Test
    void percentageDiscountShouldApplyToMultipleItems() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createPercentageDiscount("50 off", 50));

        assertEquals(new Money(30), resolver.getNewPrice(product, 3));
    }

    @Test
    void multiBuyDiscountShouldChargeForPayableQuantity() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(
            createMultiBuyDiscount("3 for 2", 3, 2)
        );

        assertEquals(new Money(40), resolver.getNewPrice(product, 3));
    }

    @Test
    void multiBuyDiscountShouldChargeFullPriceForRemainder() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(
            createMultiBuyDiscount("3 for 2", 3, 2)
        );

        // Tre varor kostar 40, den fjärde kostar ytterligare 20.
        assertEquals(new Money(60), resolver.getNewPrice(product, 4));
    }

    @Test
    void multiBuyDiscountShouldTakePriorityOverPercentageDiscount() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(
            createMultiBuyDiscount("3 for 2", 3, 2)
        );
        group.addDiscount(createPercentageDiscount("50 off", 50));

        // Multibuy ger 40; procentrabatten ska inte kombineras med den.
        assertEquals(new Money(40), resolver.getNewPrice(product, 3));
    }
    
    @Test
    void memberDiscountShouldTakePriorityOverStudentSeniorDiscount() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(100));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createMemberDiscount("Member"));
        group.addDiscount(createStudentSeniorDiscount("Student"));

        Money price = resolver.getNewPrice(product, 1);

        assertEquals(new Money(80), price);
    }
    
    @Test
    void studentSeniorDiscountShouldReducePriceByTenPercent() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(100));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createStudentSeniorDiscount("Student"));

        Money price = resolver.getNewPrice(product, 1);

        assertEquals(new Money(90), price);
    }
    
    @Test
    void invalidPercentageDiscountShouldBeIgnored() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(100));
        ProductGroup group = new ProductGroup("group");

        ZoneId zone = ZoneId.of("Europe/Stockholm");
        Clock clock = Clock.fixed(
            Instant.parse("2026-10-23T10:00:00Z"),
            zone
        );

        PercentageDiscount discount = new PercentageDiscount(
            "Future discount",
            50,
            LocalDateTime.of(2026, 10, 23, 13, 0),
            LocalDateTime.of(2029, 10, 23, 13, 0),
            clock
        );

        group.addProduct(product);
        group.addDiscount(discount);

        Money price = resolver.getNewPrice(product, 1);

        assertEquals(new Money(100), price);
    }
    
    @Test
    void invalidMultiBuyDiscountShouldBeIgnored() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        ZoneId zone = ZoneId.of("Europe/Stockholm");
        Clock clock = Clock.fixed(
            Instant.parse("2026-10-23T10:00:00Z"),
            zone
        );

        MultiBuyDiscount discount = new MultiBuyDiscount(
            "Expired offer",
            3,
            2,
            LocalDateTime.of(2020, 1, 1, 0, 0),
            LocalDateTime.of(2025, 1, 1, 0, 0),
            clock
        );

        group.addProduct(product);
        group.addDiscount(discount);

        Money price = resolver.getNewPrice(product, 3);

        assertEquals(new Money(60), price);
    }
    
    @Test
    void bestMultiBuyDiscountShouldBeSelected() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);

        group.addDiscount(
            createMultiBuyDiscount("3 for 2", 3, 2)
        );

        group.addDiscount(
            createMultiBuyDiscount("4 for 2", 4, 2)
        );

        Money price = resolver.getNewPrice(product, 4);

        assertEquals(new Money(40), price);
    }
    
    @Test
    void fixedDiscountShouldBeAppliedBeforePercentageDiscount() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(100));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(
            createFixedDiscount("10 off", new Money(10))
        );
        group.addDiscount(
            createPercentageDiscount("50 percent off", 50)
        );

        Money price = resolver.getNewPrice(product, 1);

        // 100 - 10 = 90; 50 % rabatt på 90 = 45.
        assertEquals(new Money(45), price);
    }
    
    @Test
    void multipleFixedDiscountsShouldBeAppliedInSequence() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(100));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createFixedDiscount("10 off", new Money(10)));
        group.addDiscount(createFixedDiscount("20 off", new Money(20)));

        assertEquals(new Money(70), resolver.getNewPrice(product, 1));
    }

    @Test
    void multiBuyDiscountShouldNotApplyBelowRequiredQuantity() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createMultiBuyDiscount("3 for 2", 3, 2));

        assertEquals(new Money(40), resolver.getNewPrice(product, 2));
    }

    @Test
    void equalMultiBuySavingsShouldKeepFirstDiscount() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(20));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);

        // Båda ger en besparing på en vara per erbjudande.
        group.addDiscount(createMultiBuyDiscount("3 for 2", 3, 2));
        group.addDiscount(createMultiBuyDiscount("4 for 3", 4, 3));

        // För fyra varor väljs det första erbjudandet:
        // 3 för 2 + 1 till ordinarie pris = 60.
        assertEquals(new Money(60), resolver.getNewPrice(product, 4));
    }

    @Test
    void memberDiscountShouldCombineWithOtherPercentageDiscount() {
        ProductDiscountResolver resolver = new ProductDiscountResolver();
        Product product = new Product("Milk", new Money(100));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createMemberDiscount("Member"));
        group.addDiscount(createPercentageDiscount("Coupon", 10));

        // Medlemsrabatt: 100 -> 80.
        // Kupong: 80 -> 72.
        assertEquals(new Money(72), resolver.getNewPrice(product, 1));
    }
}






