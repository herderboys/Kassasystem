package checkout;

public abstract class Discount {
	
	private String name;
	private int discountNumber;
	
	public Discount(String name,int discountNumber ) {
		
		if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Discount name cannot be null or blank"
            );
        }
		
		if (discountNumber <= 0) {
			throw new IllegalArgumentException("Discount cannot be zero or negative");			
		}
		
		this.name = name;
		this.discountNumber = discountNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDiscountNumber() {
		
		return discountNumber;
		
	}
}
