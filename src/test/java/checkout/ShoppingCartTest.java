package checkout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class ShoppingCartTest {

    @Test
    void addItemToCartTest(){



        Product product = new Product("testProduct", new Money(10));

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(product);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertTrue(cart.containsKey(product));

    }

    @Test
    void removeItemFromCart(){

        Product product = new Product("testProduct", new Money(10));

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(product);

        shoppingCart.removeItem(product);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertFalse(cart.containsKey(product));
    }



}
