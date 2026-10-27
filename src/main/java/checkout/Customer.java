package checkout;

import java.time.LocalDate;

public class Customer {
    private final String name;
    private final Money wallet;
    private Membership membership;
    private boolean StudentSenior = false;
    
    public Customer(String name, Money wallet) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet cannot be null");
        }
        this.name = name;
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public Money getWallet() {
        return wallet;
    }

    public Membership getMembership() {
        return membership;
    }
    
    public void setStudentSenior() {
        this.StudentSenior = !this.StudentSenior;
    }
    
    public boolean getStudentSenior() {
		return StudentSenior;
	}

    public boolean hasValidMembershipOn(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return membership != null && membership.isValidOn(date);
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}
