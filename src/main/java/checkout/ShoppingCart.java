package checkout;


import java.util.HashMap;

public class ShoppingCart {

    HashMap<Product, Integer> cart = new HashMap<>();

    public void addItem(Product p){
        cart.put(p, 1);
    }

    public void removeItem(Product p){
        cart.remove(p);
    }

    public HashMap<Product, Integer> getCart(){
        return cart;
    }



}
