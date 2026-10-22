package checkout;

public class Product {
    private String name;

    public Product(String name, Money money) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return null;
    }
}
