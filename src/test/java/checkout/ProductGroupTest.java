package checkout;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductGroupTest {

	@Test
	void testGetProductGroupName() {
		ProductGroup group = new ProductGroup("Drycker");
        assertEquals("Drycker", group.getName());
	}
	
	@Test
	void testIsProductGroupEmpty() {
		ProductGroup group = new ProductGroup("Drycker");
		assertEquals(0, group.getProducts().size());
	}
	
	@Test
	void testAddProductToProductGroup() {
		ProductGroup group = new ProductGroup("Drycker");
		group.addProduct();
		assertEquals(1, group.getProducts().size());
	}

}
