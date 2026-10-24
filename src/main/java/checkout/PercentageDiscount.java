package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public class PercentageDiscount extends Discount {

    public PercentageDiscount(
            String name,
            int discountNumber,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Clock clock) {

        super(name, discountNumber, startTime, endTime, clock);

        if (discountNumber > 100) {
            throw new IllegalArgumentException(
                "Percentage cannot exceed 100"
            );
        }
    }
}
