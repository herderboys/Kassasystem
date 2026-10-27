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

    public Money getPrice(){

        Money payment;
        int price = 0;

        for(Product p : cart.keySet()) {
            price += p.getPrice().getAmountInOre()* cart.get(p);
        }

        payment = new Money(price);

        if(customer.getStudentSenior()){
            payment = payment.percentage(90);

        }

        return payment;
    }



}
