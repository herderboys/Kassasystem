package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public class PercentageDiscount extends Discount {

	private final int discountPercentage;
	
    public PercentageDiscount(
            String name,
            int discountPercentage,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Clock clock) {

        super(name, startTime, endTime, clock);
        
        if (discountPercentage <= 0) {
            throw new IllegalArgumentException(
                "Discount cannot be zero or negative");
        }

        if (discountPercentage > 100) {
            throw new IllegalArgumentException(
                "Percentage cannot exceed 100"
            );
        }
        
        this.discountPercentage = discountPercentage;
                        
    }
    
    public int getDiscountPercentage() {
        return discountPercentage;
    }
    
}
