package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public class StudentSeniorDiscount extends PercentageDiscount {

    public static final int DISCOUNT_PERCENTAGE = 10;

    public StudentSeniorDiscount(
      String name,LocalDateTime startTime,LocalDateTime endTime,Clock clock) {

        super(name,DISCOUNT_PERCENTAGE,startTime,endTime,clock
        );
    }
}