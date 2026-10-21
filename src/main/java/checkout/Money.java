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

    public Money add(int amount) {
        return new Money(ore + amount);
    }

    public Money subtract(int amount) {
        return new Money(ore - amount);
    }
}
