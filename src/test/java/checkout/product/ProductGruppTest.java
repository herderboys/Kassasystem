package checkout.product;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductGruppTest {

	@Test
	void testGetNamn() {
		ProductGrupp grupp = new ProductGrupp("Drycker");

        assertEquals("Drycker", grupp.getNamn());
	}

}
