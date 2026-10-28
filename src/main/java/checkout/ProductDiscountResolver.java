package checkout;

import java.util.ArrayList;
import java.util.List;

public class ProductDiscountResolver {
	
	public Money getNewPrice(Product product, int quantity) {
        
		List<Discount> percentageDiscounts = new ArrayList<>();
		
		ProductGroup group = product.getProductGroup();
		
		Money unitPrice = product.getPrice();
		

		if (group == null){    
			//quantity * return product.getPrice();
		}
		
		List<Discount> discounts = group.getDiscounts();
		
		for (Discount discount : discounts) {
	        if (!discount.isValid()) {
	            continue;
	        }
	        
	        if (discount instanceof PercentageDiscount) {
	            percentageDiscounts.add(discount);    
	        }
	        
		}
		//
		
		for (percentageDiscounts discount : discounts) {
			 
			 
			 
		 }
		
        return null;
    }
	
}
