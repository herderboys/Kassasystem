package checkout;


import java.util.HashMap;

public class ShoppingCart {

    HashMap<Product, Integer> cart = new HashMap<>();

    public void addItem(Product p){
        cart.put(p, 1);
    }

    public HashMap<Product, Integer> getCart(){
        return cart;
    }

}
