package checkout.product;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductGruppTest {

	@Test
	void testGetNamn() {
		ProductGrupp grupp = new ProductGrupp("Drycker");
        assertEquals("Drycker", grupp.getNamn());
	}
	
	@Test
	void testIsProductGruppEmpty() {
		ProductGrupp grupp = new ProductGrupp("Drycker");
		assertEquals(0, grupp.getProducts().size());
	}

}
