import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class VaraTest {

	@Test
	void test() {
		Vara vara = new Vara("Mjöl", 15.90);
		
		assertEquals("Mjölk", vara.getNamn());
		
		fail("Not yet implemented");
	}

}
