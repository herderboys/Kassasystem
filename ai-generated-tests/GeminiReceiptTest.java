package checkout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeminiReceiptTest {

    @Test
    void testEmptyCartReceipt() {
        Customer customer = new Customer("Alice Smith", new Money(1000));
        ShoppingCart cart = new ShoppingCart(customer);

        Receipt receipt = new Receipt(cart);
        String result = receipt.getReceipt();

        // An empty cart should produce a receipt containing only the total price of 0[cite: 2, 3]
        assertTrue(result.contains("Total price: 0"), "Receipt should show a total price of 0 for an empty cart.");
    }

    @Test
    void testSingleProductReceipt() {
        Customer customer = new Customer("Bob Jones", new Money(5000));
        ShoppingCart cart = new ShoppingCart(customer);
        
        // Product with a net price of 100 öre and 25% VAT (VAT25 adds 25 öre -> total 125 öre)[cite: 3, 4, 6]
        Product product = new Product("Notebook", new Money(100), new VAT25());
        cart.addItem(product);

        Receipt receipt = new Receipt(cart);
        String result = receipt.getReceipt();

        // Verify product details and total price (100 + 25 = 125 öre)
        assertTrue(result.contains("Notebook"), "Receipt should include the product name.");
        assertTrue(result.contains("Total price: 125"), "Receipt total price should include VAT.");
    }

    @Test
    void testMultipleProductsSortingAndTotal() {
        Customer customer = new Customer("Charlie Brown", new Money(10000));
        ShoppingCart cart = new ShoppingCart(customer);

        // Add products out of alphabetical order to test sortProducts()
        Product zebraToy = new Product("Zebra Plush", new Money(200), new VATO()); // 200 öre (0% VAT)[cite: 5]
        Product apple = new Product("Apple", new Money(80), new VAT25());       // 80 + 20 = 100 öre (25% VAT)[cite: 3, 4, 6]

        cart.addItem(zebraToy);
        cart.addItem(apple);

        Receipt receipt = new Receipt(cart);
        String result = receipt.getReceipt();

        // Verify both items are present
        assertTrue(result.contains("Apple"));
        assertTrue(result.contains("Zebra Plush"));

        // Verify total price calculation: 100 (Apple) + 200 (Zebra) = 300 öre[cite: 2, 3]
        assertTrue(result.contains("Total price: 300"));

        // Verify sorting order: "Apple" should appear before "Zebra Plush" in the receipt string[cite: 1]
        int appleIndex = result.indexOf("Apple");
        int zebraIndex = result.indexOf("Zebra Plush");
        assertTrue(appleIndex < zebraIndex, "Products should be sorted alphabetically on the receipt.");
    }
}
