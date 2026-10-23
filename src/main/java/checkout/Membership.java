package checkout;

import java.time.LocalDate;

public class Membership {
    private final LocalDate expirationDate;
    private boolean active = true;

    public Membership(LocalDate expirationDate) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean isActive() {
        return active;
    }

    // An active membership is valid through its expiration date.
    public boolean isValidOn(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return active && !date.isAfter(expirationDate);
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }
}
