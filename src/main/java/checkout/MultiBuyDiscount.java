package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public class MultiBuyDiscount extends Discount {

    private final int quantityRequired;
    private final int quantityToPay;

    public MultiBuyDiscount(
            String name,
            int quantityRequired,
            int quantityToPay,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Clock clock) {

        super(name, startDate, endDate, clock);

        this.quantityRequired = quantityRequired;
        this.quantityToPay = quantityToPay;
    }

    public int getQuantityRequired() {
        return quantityRequired;
    }
    
    public int getQuantityToPay() {
        return quantityToPay;
    }
   
}
