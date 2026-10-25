package checkout;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;


class ProductGroupTest {
	
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
	

	@Test
	void testGetProductGruppName() {
		ProductGroup grupp = new ProductGroup("Drycker");
        assertEquals("Drycker", grupp.getName());
	}
	
	@Test
	void testIsProductGruppEmpty() {
		ProductGroup grupp = new ProductGroup("Drycker");
		assertEquals(0, grupp.getProducts().size());
	}
	
	@Test
	void testAddDiscountToGrupp() {
		ProductGroup grupp = new ProductGroup("Drycker");
		PercentageDiscount discount = createPercentageDiscount("Discount1", 10);
		grupp.addDiscount(discount);
		assertEquals(1, grupp.getDiscounts().size());
	}
	
	@Test
	void testAddProductToProductGrupp() {
		ProductGroup grupp = new ProductGroup("Drycker");
		Product product = new Product("Läsk", new Money(1000));
		grupp.addProduct(product);
		assertEquals(1, grupp.getProducts().size());
	}
	
	@Test
	void testRemoveProductFromProductGrupp() {
		ProductGroup grupp = new ProductGroup("Drycker");
		Product product = new Product("Läsk", new Money(1000));

	    grupp.addProduct(product);
	    boolean removed = grupp.removeProduct(product);

	    assertTrue(removed);
	    assertEquals(0, grupp.getProducts().size());
	}
	
	@Test
	void testRemoveProductThatDoesNotExist() {
		ProductGroup grupp = new ProductGroup("Drycker");
		Product product = new Product("Läsk", new Money(1000));

	    boolean removed = grupp.removeProduct(product);

	    assertFalse(removed);
	    assertEquals(0, grupp.getProducts().size());
	}
	
	
	@Test
	void testIterateOverProducts() {
		ProductGroup grupp = new ProductGroup("Drycker");
		Product product1 = new Product("Läsk", new Money(1000));
		Product product2 = new Product("Godis", new Money(1000));

	    grupp.addProduct(product1);
	    grupp.addProduct(product2);

	    int count = 0;

	    for (Object product : grupp.getProducts()) {
	        count++;
	    }

	    assertEquals(2, count);
	}
	
	@Test
	void testProductsCantBeInMoreThenOneGroup() {
		ProductGroup grupp1 = new ProductGroup("Drycker");
		ProductGroup grupp2 = new ProductGroup("Drycker");
		Product product = new Product("Godis", new Money(1000));

		grupp1.addProduct(product);

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> grupp2.addProduct(product)
	    );

	    assertEquals(1, grupp1.getProducts().size());
	    assertEquals(0, grupp2.getProducts().size());
	}
	
	
	@Test
	void testGetRemovedDiscount() {
		
		
		PercentageDiscount discount = createPercentageDiscount("Discount1", 10);
	    assertEquals("Discount1", discount.getName());
		
		ProductGroup grupp = new ProductGroup("Drycker");

	    grupp.addDiscount(discount);
	    
	    grupp.removeDiscount(discount);

	    assertSame(discount, grupp.getDiscounts().get(0));
	}
}
