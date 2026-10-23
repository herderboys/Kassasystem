package checkout.product;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import checkout.discount.PercentageDiscount;

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
	void testAddDiscountToGrupp() {
		ProductGrupp grupp = new ProductGrupp("Drycker");
		PercentageDiscount discount = new PercentageDiscount("Sommarrea", 10);
		grupp.addDiscount(discount);
		assertEquals(1, grupp.getDiscounts().size());
	}
	
	@Test
	void testAddProductToProductGrupp() {
		ProductGrupp grupp = new ProductGrupp("Drycker");
		grupp.AddProductToProductGrupp();
		assertEquals(1, grupp.getProducts().size());
	}
	
}
