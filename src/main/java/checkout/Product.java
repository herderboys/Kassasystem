package checkout;

public class Product {
    private String name;
    private Money price;

    public Product(String name, Money price) {
        if (name == null|| name.isBlank()) {
            throw new IllegalArgumentException("Name can't be null.");
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
