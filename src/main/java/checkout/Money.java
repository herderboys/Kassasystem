package checkout;

public class Money {
    private final int ore;

    public Money(int ore) {
        if (ore < 0) {
            throw new IllegalArgumentException("Amount cannot be below 0: " + ore);
        }
        this.ore = ore;
    }

    @Override 
    public boolean equals(Object other) {
        if (other instanceof Money m) {
            return this.ore == m.ore ;
        }
        return false;
    }

    @Override 
    public int hashCode() {
        return ore;
    }

    public int getAmountInOre() {
        return ore;
    }

    public Money add(Money other) {
        return new Money(ore + other.getAmountInOre());
    }

    public Money subtract(Money other) {
        return new Money(ore - other.getAmountInOre());
    }

    public Money percentage(int percent) {
        return new Money(ore * percent / 100);
    }
}
