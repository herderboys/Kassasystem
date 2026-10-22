package checkout;

public class Product {
    private String name;
    private Money money;

    public Product(String name, Money money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return money;
    }
}
