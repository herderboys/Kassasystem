package checkout;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    private final LocalDate date = LocalDate.of(2026, 10, 23);

    @Test
    void invalidCustomerDetailsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Customer(null, new Money(0)));
        assertThrows(IllegalArgumentException.class, () -> new Customer("", new Money(0)));
        assertThrows(IllegalArgumentException.class, () -> new Customer("   ", new Money(0)));
        assertThrows(IllegalArgumentException.class, () -> new Customer("Abbe", null));
    }

    @Test
    void missingMembershipCheckDateIsRejectedWithOrWithoutMembership() {
        Customer customer = new Customer("Abbe", new Money(0));
        assertThrows(IllegalArgumentException.class, () -> customer.hasValidMembershipOn(null));
        customer.setMembership(new Membership(date));
        assertThrows(IllegalArgumentException.class, () -> customer.hasValidMembershipOn(null));
    }

    @Test
    void customerHasNameAndMoneyWithoutMembership() {
        Money wallet = new Money(500);
        Customer customer = new Customer("Abbe", wallet);
        assertEquals("Abbe", customer.getName());
        assertSame(wallet, customer.getWallet());
        assertNull(customer.getMembership());
        assertFalse(customer.hasValidMembershipOn(date));
    }

    @Test
    void membershipCanBeLinkedReplacedAndRemoved() {
        Customer customer = new Customer("Abbe", new Money(0));
        Membership membership = new Membership(date);
        customer.setMembership(membership);
        assertSame(membership, customer.getMembership());
        assertTrue(customer.hasValidMembershipOn(date));
        membership.deactivate();
        assertFalse(customer.hasValidMembershipOn(date));

        customer.setMembership(new Membership(date.minusDays(1)));
        assertFalse(customer.hasValidMembershipOn(date));

        customer.setMembership(null);
        assertNull(customer.getMembership());
        assertFalse(customer.hasValidMembershipOn(date));
    }
}
