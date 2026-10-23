package checkout;

public class PercentageDiscount extends Discount{

	public PercentageDiscount(String name, int discountNumber) {
		super(name, discountNumber);
		
		if (discountNumber > 100) {
            throw new IllegalArgumentException(
                "Percentage cannot exceed 100"
            );
        }
		
		
	}

}
