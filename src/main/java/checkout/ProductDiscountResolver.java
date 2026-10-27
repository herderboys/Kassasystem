package checkout;

import java.util.ArrayList;
import java.util.List;

public class ProductDiscountResolver {
	
	public Money getNewPrice(Product product, int quantity) {
        
		ProductGroup group = product.getProductGroup();

		if (group == null){    
			return product.getPrice();
		}
		
		List<Discount> discounts = group.getDiscounts();
		
		for (Discount discount : discounts) {
	        if (!discount.isValid()) {
	            continue;
	        }
		}
        return null;
    }
	
}
