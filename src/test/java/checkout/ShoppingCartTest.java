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
    void removeItemFromCartTest(){

        Product product = new Product("testProduct", new Money(10));

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(product);

        shoppingCart.removeItem(product);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertFalse(cart.containsKey(product));
    }

    @Test
    void addDifferentItemToCartTest(){

        Product product = new Product("testProduct", new Money(10));

        Product product2 = new Product("testProduct2", new Money(10));


        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(product);

        shoppingCart.addItem(product2);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertTrue(cart.containsKey(product) && cart.containsKey(product2));


    }

    @Test
    void addSameItemToCartTest(){

        Product product = new Product("testProduct", new Money(10));


        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(product);

        shoppingCart.addItem(product);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertTrue(cart.containsValue(2));

    }

    @Test
    void removeItemWithMultipleTest(){

        Product product = new Product("testProduct", new Money(10));


        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(product);

        shoppingCart.addItem(product);

        shoppingCart.removeItem(product);

        HashMap<Product, Integer> cart = shoppingCart.getCart();


        assertTrue(cart.containsValue(1));



    }

    @Test
    void getPriceOfCartTest(){

        Product product = new Product("testProduct", new Money(10));


        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(product);

        shoppingCart.addItem(product);


        Money price = shoppingCart.getPrice();



        assertEquals(20, price.getAmountInOre());

    }



}
