package checkout.product;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductGruppTest {

	@Test
	void testGetProductGruppName() {
		ProductGrupp grupp = new ProductGrupp("Drycker");
        assertEquals("Drycker", grupp.getName());
	}
	
	@Test
	void testIsProductGruppEmpty() {
		ProductGrupp grupp = new ProductGrupp("Drycker");
		assertEquals(0, grupp.getProducts().size());
	}
	
	@Test
	void testAddProductToProductGrupp() {
		ProductGrupp grupp = new ProductGrupp("Drycker");
		grupp.addProduct();
		assertEquals(1, grupp.getProducts().size());
	}

}
