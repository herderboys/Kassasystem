package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public class MemberDiscount extends PercentageDiscount {

    public static final int DISCOUNT_PERCENTAGE = 20;

    public MemberDiscount(
       String name,LocalDateTime startTime,LocalDateTime endTime,Clock clock) {

        super(name,DISCOUNT_PERCENTAGE,startTime,endTime,clock);
    }
}