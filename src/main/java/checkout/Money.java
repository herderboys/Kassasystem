package checkout;

public class Money {
    private int ore;

    public Money(int ore) {
        if (ore < 0) {
            throw new IllegalArgumentException();
        }
        this.ore = ore;
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
}
