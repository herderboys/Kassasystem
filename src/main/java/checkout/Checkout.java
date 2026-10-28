package checkout;

public class Checkout {
    private final Scanner scanner;
    private final ProductRegister register;

    public Checkout(Scanner scanner, ProductRegister register) {
        this.scanner = scanner;
        this.register = register;
    }

    public Product scanItem() {
        Gtin gtin = scanner.scan();
        return register.getProduct(gtin);
    }
}
