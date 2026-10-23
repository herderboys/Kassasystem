package checkout;

public class Product {
    private final String name;
    private final Money price;

    public Product(String name, Money price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty or null.");
        }
        if (price == null) {
            throw new IllegalArgumentException("Money can't be null.");
        }

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}
