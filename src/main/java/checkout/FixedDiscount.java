package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public class FixedDiscount extends Discount {

	public FixedDiscount(
            String name,
            int discountNumber,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Clock clock) {

        super(name, discountNumber, startTime, endTime, clock);

}

	
}