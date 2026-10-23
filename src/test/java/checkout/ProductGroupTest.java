package checkout;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import checkout.product.PercentageDiscount;
import checkout.product.ProductGrupp;

class ProductGroupTest {

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
		PercentageDiscount discount = new PercentageDiscount("Sommarrea", 10);
		grupp.addDiscount(discount);
		assertEquals(1, grupp.getDiscounts().size());
	}
	
	@Test
	void testAddProductToProductGrupp() {
		ProductGroup grupp = new ProductGroup("Drycker");
		Object product = new Object();
		grupp.addProduct(product);
		assertEquals(1, grupp.getProducts().size());
	}
	
	@Test
	void testRemoveProductFromProductGrupp() {
		ProductGroup grupp = new ProductGroup("Drycker");
	    Object product = new Object();

	    grupp.addProduct(product);
	    boolean removed = grupp.removeProduct(product);

	    assertTrue(removed);
	    assertEquals(0, grupp.getProducts().size());
	}
	
	@Test
	void testRemoveProductThatDoesNotExist() {
		ProductGroup grupp = new ProductGroup("Drycker");
	    Object product = new Object();

	    boolean removed = grupp.removeProduct(product);

	    assertFalse(removed);
	    assertEquals(0, grupp.getProducts().size());
	}
	
	
	@Test
	void testIterateOverProducts() {
		ProductGroup grupp = new ProductGroup("Drycker");
	    Object product1 = new Object();
	    Object product2 = new Object();

	    grupp.addProduct(product1);
	    grupp.addProduct(product2);

	    int count = 0;

	    for (Object product : grupp.getProducts()) {
	        count++;
	    }

	    assertEquals(2, count);
	}
	
	@Test
	void testGetAddedDiscount() {
		ProductGroup grupp = new ProductGroup("Drycker");
	    PercentageDiscount discount =
	        new PercentageDiscount("Sommarrea", 10);

	    grupp.addDiscount(discount);

	    assertSame(discount, grupp.getDiscounts().get(0));
	}
}
