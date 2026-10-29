package checkout;

import java.util.HashMap;

public class ShoppingCart {
    private final Customer customer;
    private final HashMap<Product, Integer> cart = new HashMap<>();
    private final ProductDiscountResolver discountResolver =
            new ProductDiscountResolver();

    public ShoppingCart(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException(
                "Customer cannot be null"
            );
        }

        this.customer = c;
    }

    public void addItem(Product p) {
        if (p == null) {
            throw new IllegalArgumentException(
                "Product cannot be null"
            );
        }

        cart.put(p, cart.getOrDefault(p, 0) + 1);
    }

    public void removeItem(Product p) {
        if (p == null || !cart.containsKey(p)) {
            return;
        }

        if (cart.get(p) > 1) {
            cart.put(p, cart.get(p) - 1);
        } else {
            cart.remove(p);
        }
    }

    public HashMap<Product, Integer> getCart() {
        return new HashMap<>(cart);
    }

    public HashMap<Product, Integer> getPriceList() {
        HashMap<Product, Integer> priceList = new HashMap<>();

        for (Product product : cart.keySet()) {
            int quantity = cart.get(product);

            // Beräkna priset med hänsyn till kundens status.
            Money price = discountResolver.getNewPrice(
                product, quantity, customer
            );

            // Lägg till moms på det rabatterade priset.
            Money vat = product.getVAT()
                .calculateVatFromNetPrice(price);

            Money priceWithVat = price.add(vat);

            priceList.put(
                product, priceWithVat.getAmountInOre()
            );
        }

        return priceList;
    }

    public Money getPrice() {
        Money total = new Money(0);

        for (int price : getPriceList().values()) {
            total = total.add(new Money(price));
        }

        return total;
    }
}