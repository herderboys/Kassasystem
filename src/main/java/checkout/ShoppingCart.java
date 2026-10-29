package checkout;

import java.util.HashMap;

public class ShoppingCart {
    private final Customer customer;
    private final HashMap<Product, Integer> cart = new HashMap<>();
    private final ProductDiscountResolver discountResolver =
            new ProductDiscountResolver();

    public ShoppingCart(Customer c) {
        customer = c;
    }

    public void addItem(Product p) {
        cart.put(p, cart.getOrDefault(p, 0) + 1);
    }

    public void removeItem(Product p) {
        if (!cart.containsKey(p)) {
            return;
        }

        if (cart.get(p) > 1) {
            cart.put(p, cart.get(p) - 1);
        } else {
            cart.remove(p);
        }
    }

    public HashMap<Product, Integer> getCart() {
        return cart;
    }

    public HashMap<Product, Integer> getPriceList() {
        HashMap<Product, Integer> priceList = new HashMap<>();

        for (Product p : cart.keySet()) {
            int quantity = cart.get(p);

            // Skicka produkt och antal till rabattberäknaren.
            Money price = discountResolver.getNewPrice(p, quantity);

            // Lägg till moms på det rabatterade nettopriset.
            Money vat = p.getVAT().calculateVatFromNetPrice(price);
            Money priceWithVat = price.add(vat);

            priceList.put(p, priceWithVat.getAmountInOre());
        }

        return priceList;
    }

    public Money getPrice() {
        HashMap<Product, Integer> priceList = getPriceList();
        Money total = new Money(0);

        for (int price : priceList.values()) {
            total = total.add(new Money(price));
        }

        return total;
    }
}
