package checkout;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class MembershipTest {
    private final LocalDate date = LocalDate.of(2026, 10, 23);

    @Test
    void missingDatesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Membership(null));
        Membership membership = new Membership(date);
        assertThrows(IllegalArgumentException.class, () -> membership.isValidOn(null));
        membership.deactivate();
        assertThrows(IllegalArgumentException.class, () -> membership.isValidOn(null));
    }

    @Test
    void activeMembershipIsValidThroughExpirationDate() {
        Membership membership = new Membership(date);
        assertTrue(membership.isActive());
        assertEquals(date, membership.getExpirationDate());
        assertTrue(membership.isValidOn(date.minusDays(1)));
        assertTrue(membership.isValidOn(date));
    }

    @Test
    void inactiveMembershipCanBeActivatedAgain() {
        Membership membership = new Membership(date);
        membership.deactivate();
        assertFalse(membership.isActive());
        assertFalse(membership.isValidOn(date));

        membership.activate();
        assertTrue(membership.isActive());
        assertTrue(membership.isValidOn(date));
        assertFalse(membership.isValidOn(date.plusDays(1)));
    }

    @Test
    void expiredMembershipIsNotValid() {
        Membership membership = new Membership(date.minusDays(1));
        assertFalse(membership.isValidOn(date));
    }
}
