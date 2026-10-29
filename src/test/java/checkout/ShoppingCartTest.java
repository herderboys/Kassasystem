package checkout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ShoppingCartTest {
	
	private MemberDiscount createMemberDiscountForTest() {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");
	    Clock clock = Clock.fixed(
	        Instant.parse("2026-10-23T10:00:00Z"),
	        zone
	    );

	    return new MemberDiscount(
	        "Member discount",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        clock
	    );
	}

	private StudentSeniorDiscount createStudentDiscountForTest() {
	    ZoneId zone = ZoneId.of("Europe/Stockholm");
	    Clock clock = Clock.fixed(
	        Instant.parse("2026-10-23T10:00:00Z"),
	        zone
	    );

	    return new StudentSeniorDiscount(
	        "Student discount",
	        LocalDateTime.of(2026, 10, 23, 11, 0),
	        LocalDateTime.of(2029, 10, 23, 13, 0),
	        clock
	    );
	}
	

    @Test
    void addItemToCartTest(){

        Product product = new Product("testProduct", new Money(10));

        ShoppingCart shoppingCart = new ShoppingCart(new Customer("customer", new Money(10)));

        shoppingCart.addItem(product);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertTrue(cart.containsKey(product));

    }

    @Test
    void removeItemFromCartTest(){

        Product product = new Product("testProduct", new Money(10));

        ShoppingCart shoppingCart = new ShoppingCart(new Customer("customer", new Money(10)));

        shoppingCart.addItem(product);

        shoppingCart.removeItem(product);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertFalse(cart.containsKey(product));
    }

    @Test
    void addDifferentItemToCartTest(){

        Product product = new Product("testProduct", new Money(10));
        Product product2 = new Product("testProduct2", new Money(10));

        ShoppingCart shoppingCart = new ShoppingCart(new Customer("customer", new Money(10)));

        shoppingCart.addItem(product);
        shoppingCart.addItem(product2);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertTrue(cart.containsKey(product) && cart.containsKey(product2));


    }

    @Test
    void addSameItemToCartTest(){

        Product product = new Product("testProduct", new Money(10));

        ShoppingCart shoppingCart = new ShoppingCart(new Customer("customer", new Money(10)));

        shoppingCart.addItem(product);
        shoppingCart.addItem(product);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertTrue(cart.containsValue(2));

    }

    @Test
    void removeItemWithMultipleTest(){

        Product product = new Product("testProduct", new Money(10));

        ShoppingCart shoppingCart = new ShoppingCart(new Customer("customer", new Money(10)));

        shoppingCart.addItem(product);
        shoppingCart.addItem(product);

        shoppingCart.removeItem(product);

        HashMap<Product, Integer> cart = shoppingCart.getCart();

        assertTrue(cart.containsValue(1));

    }

    @Test
    void getPriceOfCartTest(){

        Product product = new Product("testProduct", new Money(10));

        ShoppingCart shoppingCart = new ShoppingCart(new Customer("customer", new Money(10)));

        shoppingCart.addItem(product);
        shoppingCart.addItem(product);

        Money price = shoppingCart.getPrice();

        assertEquals(25, price.getAmountInOre());

    }

    @Test
    void calculatePriceTest(){

        Product product = new Product("testProduct", new Money(10));
        Product product1 = new Product("testProduct1", new Money(15));

        ShoppingCart shoppingCart = new ShoppingCart(new Customer("customer", new Money(10)));

        shoppingCart.addItem(product);
        shoppingCart.addItem(product);
        shoppingCart.addItem(product1);

        Money price = shoppingCart.getPrice();

        assertEquals(43, price.getAmountInOre(), "price is " + price.getAmountInOre() + ", price should be 43");

    }

    @Test
    void studentSeniorDiscountTest() {
        Customer customer = new Customer("customer", new Money(10));
        customer.setStudentSenior(true);

        Product product = new Product("testProduct", new Money(10));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);

        Clock clock = Clock.fixed(
            Instant.parse("2026-10-23T10:00:00Z"),
            ZoneId.of("Europe/Stockholm")
        );

        StudentSeniorDiscount discount = new StudentSeniorDiscount(
            "Student discount",
            LocalDateTime.of(2026, 10, 23, 11, 0),
            LocalDateTime.of(2029, 10, 23, 13, 0),
            clock
        );

        group.addDiscount(discount);

        ShoppingCart shoppingCart = new ShoppingCart(customer);
        shoppingCart.addItem(product);

        assertEquals(11, shoppingCart.getPrice().getAmountInOre());
    }
    
    @Test
    void regularCustomerShouldNotGetStudentDiscount() {
        Customer customer = new Customer("customer", new Money(10));
        Product product = new Product("testProduct", new Money(10));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createStudentDiscountForTest());

        ShoppingCart cart = new ShoppingCart(customer);
        cart.addItem(product);

        assertEquals(12, cart.getPrice().getAmountInOre());
    }
    
    @Test
    void customerWithValidMembershipShouldGetMemberDiscount() {
        Customer customer = new Customer("customer", new Money(100));
        customer.setMembership(
            new Membership(LocalDate.now().plusDays(30))
        );

        Product product = new Product("testProduct", new Money(100));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createMemberDiscountForTest());

        ShoppingCart cart = new ShoppingCart(customer);
        cart.addItem(product);

        assertEquals(100, cart.getPrice().getAmountInOre());
    }
    
    @Test
    void memberWhoIsAlsoStudentShouldOnlyGetMemberDiscount() {
        Customer customer = new Customer("customer", new Money(100));
        customer.setMembership(
            new Membership(LocalDate.now().plusDays(30))
        );
        customer.setStudentSenior(true);

        Product product = new Product("testProduct", new Money(100));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createMemberDiscountForTest());
        group.addDiscount(createStudentDiscountForTest());

        ShoppingCart cart = new ShoppingCart(customer);
        cart.addItem(product);

        assertEquals(100, cart.getPrice().getAmountInOre());
    }
    
    @Test
    void customerWithExpiredMembershipShouldNotGetMemberDiscount() {
        Customer customer = new Customer("customer", new Money(100));
        customer.setMembership(
            new Membership(LocalDate.now().minusDays(1))
        );

        Product product = new Product("testProduct", new Money(100));
        ProductGroup group = new ProductGroup("group");

        group.addProduct(product);
        group.addDiscount(createMemberDiscountForTest());

        ShoppingCart cart = new ShoppingCart(customer);
        cart.addItem(product);

        assertEquals(125, cart.getPrice().getAmountInOre());
    }

    @Test
    void emptyCartShouldHaveZeroPrice() {
        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        assertEquals(0, cart.getPrice().getAmountInOre());
    }

    @Test
    void removingNonExistingItemShouldNotChangeCart() {
        Product product = new Product("product", new Money(10));

        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        assertDoesNotThrow(() -> cart.removeItem(product));
        assertTrue(cart.getCart().isEmpty());
    }

    @Test
    void removingItemTwiceShouldEmptyCart() {
        Product product = new Product("product", new Money(10));

        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        cart.addItem(product);
        cart.removeItem(product);
        cart.removeItem(product);

        assertFalse(cart.getCart().containsKey(product));
    }

    @Test
    void addingThreeSameItemsShouldGiveQuantityThree() {
        Product product = new Product("product", new Money(10));

        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        cart.addItem(product);
        cart.addItem(product);
        cart.addItem(product);

        assertEquals(3, cart.getCart().get(product));
    }

    @Test
    void removingOneOfThreeItemsShouldLeaveTwo() {
        Product product = new Product("product", new Money(10));

        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        cart.addItem(product);
        cart.addItem(product);
        cart.addItem(product);

        cart.removeItem(product);

        assertEquals(2, cart.getCart().get(product));
    }

    @Test
    void addingDifferentProductsShouldKeepQuantitiesSeparate() {
        Product product1 = new Product("product1", new Money(10));
        Product product2 = new Product("product2", new Money(20));

        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        cart.addItem(product1);
        cart.addItem(product1);
        cart.addItem(product2);

        assertEquals(2, cart.getCart().get(product1));
        assertEquals(1, cart.getCart().get(product2));
        assertEquals(2, cart.getCart().size());
    }
    
    @Test
    void getPriceListShouldContainAllProducts() {
        Product product1 = new Product("product1", new Money(10));
        Product product2 = new Product("product2", new Money(20));

        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        cart.addItem(product1);
        cart.addItem(product2);

        HashMap<Product, Integer> priceList = cart.getPriceList();

        assertEquals(2, priceList.size());
        assertTrue(priceList.containsKey(product1));
        assertTrue(priceList.containsKey(product2));
    }

    @Test
    void getPriceShouldIncreaseWhenItemIsAdded() {
        Product product = new Product("product", new Money(10));

        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        int initialPrice = cart.getPrice().getAmountInOre();

        cart.addItem(product);

        int newPrice = cart.getPrice().getAmountInOre();

        assertTrue(newPrice > initialPrice);
    }

    @Test
    void removingItemShouldReduceTotalPrice() {
        Product product = new Product("product", new Money(10));

        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        cart.addItem(product);
        cart.addItem(product);

        int priceWithTwoItems = cart.getPrice().getAmountInOre();

        cart.removeItem(product);

        int priceWithOneItem = cart.getPrice().getAmountInOre();

        assertTrue(priceWithOneItem < priceWithTwoItems);
    }

    @Test
    void getPriceShouldBeZeroAfterRemovingAllItems() {
        Product product = new Product("product", new Money(10));

        ShoppingCart cart = new ShoppingCart(
            new Customer("customer", new Money(10))
        );

        cart.addItem(product);
        cart.removeItem(product);

        assertEquals(0, cart.getPrice().getAmountInOre());
    }
    
    @Test
    void zeroQuantityShouldReturnZeroPrice() {
        Product product = new Product("product", new Money(10));
        ProductDiscountResolver resolver = new ProductDiscountResolver();

        Money price = resolver.getNewPrice(product, 0);

        assertEquals(0, price.getAmountInOre());
    }

    @Test
    void negativeQuantityShouldThrowException() {
        Product product = new Product("product", new Money(10));
        ProductDiscountResolver resolver = new ProductDiscountResolver();

        assertThrows(
            IllegalArgumentException.class,
            () -> resolver.getNewPrice(product, -1)
        );
    }

    @Test
    void productWithoutGroupShouldHaveNormalPriceTimesQuantity() {
        Product product = new Product("product", new Money(10));
        ProductDiscountResolver resolver = new ProductDiscountResolver();

        Money price = resolver.getNewPrice(product, 3);

        assertEquals(30, price.getAmountInOre());
    }
}
