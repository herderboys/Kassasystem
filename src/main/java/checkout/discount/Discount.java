package checkout.discount;

public class Discount {
	
	private String name;
	private int discountNumber;
	
	public Discount(String name,int discountNumber ) {
		
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
