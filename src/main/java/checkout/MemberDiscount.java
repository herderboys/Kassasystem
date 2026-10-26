package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public class MemberDiscount extends Discount {

	public MemberDiscount(String name, LocalDateTime startTime, LocalDateTime endTime, Clock clock) {
		super(name, startTime, endTime, clock);
		// TODO Auto-generated constructor stub
	}

}
