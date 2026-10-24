package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public class FixedDiscount extends Discount {

    private final Money discountAmount;

    public FixedDiscount(
            String name,
            Money discountAmount,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Clock clock) {

        super(name, startTime, endTime, clock);

        if (discountAmount == null) {
            throw new IllegalArgumentException(
                "Discount amount cannot be null");
        }

        if (discountAmount.getAmountInOre() <= 0) {
            throw new IllegalArgumentException(
                "Discount amount must be positive");
        }

        this.discountAmount = discountAmount;
    }

    public Money getDiscountAmount() {
        return discountAmount;
    }
}