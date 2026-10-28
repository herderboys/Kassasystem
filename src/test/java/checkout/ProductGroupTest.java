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
	void testRejectsNullName() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new ProductGroup(null)
	    );
	}

	@Test
	void testRejectsEmptyName() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new ProductGroup("")
	    );
	}

	@Test
	void testRejectsBlankName() {
	    assertThrows(
	        IllegalArgumentException.class,
	        () -> new ProductGroup("   ")
	    );
	}
	
	@Test
	void testRejectsNullProduct() {
	    ProductGroup grupp = new ProductGroup("Drycker");

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> grupp.addProduct(null)
	    );

	    assertEquals(0, grupp.getProducts().size());
	}
	
	@Test
	void testGetProductsReturnsCopy() {
	    ProductGroup grupp = new ProductGroup("Drycker");
	    Product product = new Product("Läsk", new Money(1000));

	    grupp.addProduct(product);

	    var products = grupp.getProducts();
	    products.clear();

	    assertEquals(1, grupp.getProducts().size());
	}
	
	@Test
	void testRemovedProductCanBeAddedToAnotherGroup() {
	    ProductGroup grupp1 = new ProductGroup("Drycker");
	    ProductGroup grupp2 = new ProductGroup("Mat");
	    Product product = new Product("Läsk", new Money(1000));

	    grupp1.addProduct(product);
	    grupp1.removeProduct(product);

	    assertDoesNotThrow(() -> grupp2.addProduct(product));

	    assertEquals(0, grupp1.getProducts().size());
	    assertEquals(1, grupp2.getProducts().size());
	    assertSame(grupp2, product.getProductGroup());
	}
	
	@Test
	void testRemovingProductResetsProductGroup() {
	    ProductGroup grupp = new ProductGroup("Drycker");
	    Product product = new Product("Läsk", new Money(1000));

	    grupp.addProduct(product);

	    grupp.removeProduct(product);

	    assertNull(product.getProductGroup());
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
	void testGetDiscountsReturnsCopy() {
	    ProductGroup grupp = new ProductGroup("Drycker");
	    PercentageDiscount discount =
	        createPercentageDiscount("Discount1", 10);

	    grupp.addDiscount(discount);

	    var discounts = grupp.getDiscounts();
	    discounts.clear();

	    assertEquals(1, grupp.getDiscounts().size());
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
	void testDoesNotAddSameProductTwice() {
	    ProductGroup grupp = new ProductGroup("Drycker");
	    Product product = new Product("Läsk", new Money(1000));

	    grupp.addProduct(product);
	    grupp.addProduct(product);

	    assertEquals(1, grupp.getProducts().size());
	}
	
	@Test
	void testAddingProductSetsItsProductGroup() {
	    ProductGroup grupp = new ProductGroup("Drycker");
	    Product product = new Product("Läsk", new Money(1000));

	    grupp.addProduct(product);

	    assertSame(grupp, product.getProductGroup());
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
		ProductGroup grupp2 = new ProductGroup("mat");
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
	    PercentageDiscount discount =
	        createPercentageDiscount("Discount1", 10);

	    ProductGroup grupp = new ProductGroup("Drycker");

	    grupp.addDiscount(discount);
	    grupp.removeDiscount(discount);

	    assertFalse(grupp.getDiscounts().contains(discount));
	    assertEquals(0, grupp.getDiscounts().size());
	}
	
	@Test
	void testAddDiscountRejectsNull() {
	    ProductGroup grupp = new ProductGroup("Drycker");

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> grupp.addDiscount(null)
	    );

	    assertTrue(grupp.getDiscounts().isEmpty());
	}

	@Test
	void testAddMemberDiscount() {
	    ProductGroup grupp = new ProductGroup("Drycker");

	    MemberDiscount discount = new MemberDiscount(
	        "Member discount",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        Clock.fixed(
	            Instant.parse("2026-10-23T10:00:00Z"),
	            ZoneId.of("Europe/Stockholm")
	        )
	    );

	    grupp.addDiscount(discount);

	    assertEquals(1, grupp.getDiscounts().size());
	    assertSame(discount, grupp.getDiscounts().get(0));
	}

	@Test
	void testRejectsDuplicateMemberDiscount() {
	    ProductGroup grupp = new ProductGroup("Drycker");

	    grupp.addDiscount(new MemberDiscount(
	        "Member discount 1",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        Clock.systemUTC()
	    ));

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> grupp.addDiscount(new MemberDiscount(
	            "Member discount 2",
	            LocalDateTime.of(2026, 10, 23, 11, 0),
	            LocalDateTime.of(2029, 10, 23, 13, 0),
	            Clock.systemUTC()
	        ))
	    );

	    assertEquals(1, grupp.getDiscounts().size());
	}

	@Test
	void testAddStudentSeniorDiscount() {
	    ProductGroup grupp = new ProductGroup("Drycker");

	    StudentSeniorDiscount discount = new StudentSeniorDiscount(
	        "Student discount",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        Clock.systemUTC()
	    );

	    grupp.addDiscount(discount);

	    assertEquals(1, grupp.getDiscounts().size());
	    assertSame(discount, grupp.getDiscounts().get(0));
	}

	@Test
	void testRejectsDuplicateStudentSeniorDiscount() {
	    ProductGroup grupp = new ProductGroup("Drycker");

	    grupp.addDiscount(new StudentSeniorDiscount(
	        "Student discount 1",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        Clock.systemUTC()
	    ));

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> grupp.addDiscount(new StudentSeniorDiscount(
	            "Student discount 2",
	            LocalDateTime.of(2026, 10, 23, 11, 0),
	            LocalDateTime.of(2029, 10, 23, 13, 0),
	            Clock.systemUTC()
	        ))
	    );

	    assertEquals(1, grupp.getDiscounts().size());
	}

	@Test
	void testAllowsMemberAndStudentSeniorDiscountTogether() {
	    ProductGroup grupp = new ProductGroup("Drycker");

	    grupp.addDiscount(new MemberDiscount(
	        "Member discount",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        Clock.systemUTC()
	    ));

	    grupp.addDiscount(new StudentSeniorDiscount(
	        "Student discount",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        Clock.systemUTC()
	    ));

	    assertEquals(2, grupp.getDiscounts().size());
	}
}
