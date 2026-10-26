package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public class StudentSeniorDiscount extends Discount{

	public StudentSeniorDiscount(String name, LocalDateTime startTime, LocalDateTime endTime, Clock clock) {
		super(name, startTime, endTime, clock);
		// TODO Auto-generated constructor stub
	}

}
