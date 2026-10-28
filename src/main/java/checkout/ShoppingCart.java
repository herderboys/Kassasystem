package checkout;

import java.util.HashMap;

public class ShoppingCart {
    private final Customer customer;
    private final HashMap<Product, Integer> cart = new HashMap<>();

    public ShoppingCart(Customer c){
        customer = c;
    }

    public void addItem(Product p){
        if (cart.containsKey(p)){
            cart.put(p, cart.get(p) + 1);
        }
        else cart.put(p, 1);
    }

    public void removeItem(Product p){
        if(cart.get(p) > 1){
            cart.put(p, cart.get(p) - 1);
        }
        else cart.remove(p);
    }

    public HashMap<Product, Integer> getCart(){
        return cart;
    }

    public HashMap<Product,Integer> getPriceList(){
        HashMap<Product, Integer> priceList = new HashMap<>();

        for(Product p : cart.keySet()) {
            Money price = new Money(p.getPrice().getAmountInOre()* cart.get(p));

            price = new Money(price.getAmountInOre() + (p.getVAT().calculateVatFromNetPrice(price)).getAmountInOre());

            priceList.put(p, price.getAmountInOre());

        }

        return priceList;
    }

    public Money getPrice(){
        HashMap<Product, Integer> priceList = getPriceList();
        Money price = new Money(0);
        for(Product p : priceList.keySet()){
            price = price.add(new Money(priceList.get(p)));
        }

        return price;
    }



}
