package checkout;


import java.util.HashMap;

public class ShoppingCart {

    HashMap<Product, Integer> cart = new HashMap<>();

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



}
