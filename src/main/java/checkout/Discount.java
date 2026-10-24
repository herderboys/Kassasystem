package checkout;

import java.time.Clock;
import java.time.LocalDateTime;

public abstract class Discount {

    private final String name;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Clock clock;

    public Discount(
            String name,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Clock clock) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Discount name cannot be null or blank");
        }
       

        if (startTime == null || endTime == null || clock == null) {
            throw new IllegalArgumentException(
                "Start time, end time and clock cannot be null");
        }

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException(
                "End time cannot be before start time");
        }

        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.clock = clock;
    }

    public String getName() {
        return name;
    }

    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now(clock);

        return !now.isBefore(startTime) && !now.isAfter(endTime);
    }
}
