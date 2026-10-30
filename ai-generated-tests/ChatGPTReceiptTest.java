package checkout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatGPTReceiptTest {

    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer("Test customer", new Money(10_000));
        cart = new ShoppingCart(customer);
    }

    @Test
    void emptyCartContainsOnlyZeroTotal() {
        assertEquals("Total price: 0", new Receipt(cart).getReceipt());
    }

    @Test
    void oneProductShowsNameQuantityPriceIncludingVatAndTotal() {
        cart.addItem(new Product("Milk", new Money(1_000), new VAT12()));

        assertEquals("Milk 1 1120\nTotal price: 1120", new Receipt(cart).getReceipt());
    }

    @Test
    void productsAreSortedAlphabeticallyRegardlessOfInsertionOrder() {
        cart.addItem(new Product("Zucchini", new Money(100), new VAT0()));
        cart.addItem(new Product("Apple", new Money(200), new VAT0()));
        cart.addItem(new Product("Bread", new Money(300), new VAT0()));

        assertEquals(
                "Apple 1 200\nBread 1 300\nZucchini 1 100\nTotal price: 600",
                new Receipt(cart).getReceipt());
    }

    @Test
    void repeatedProductIsShownAsOneLineWithItsQuantityAndCombinedPrice() {
        Product bread = new Product("Bread", new Money(200), new VAT25());
        cart.addItem(bread);
        cart.addItem(bread);
        cart.addItem(bread);

        assertEquals("Bread 3 750\nTotal price: 750", new Receipt(cart).getReceipt());
    }

    @Test
    void receiptUsesEachProductsVatRate() {
        cart.addItem(new Product("A Zero VAT", new Money(1_000), new VAT0()));
        cart.addItem(new Product("B Six VAT", new Money(1_000), new VAT6()));
        cart.addItem(new Product("C Twelve VAT", new Money(1_000), new VAT12()));
        cart.addItem(new Product("D Twenty Five VAT", new Money(1_000), new VAT25()));

        assertEquals(
                "A Zero VAT 1 1000\n" +
                        "B Six VAT 1 1060\n" +
                        "C Twelve VAT 1 1120\n" +
                        "D Twenty Five VAT 1 1250\n" +
                        "Total price: 4430",
                new Receipt(cart).getReceipt());
    }

    @Test
    void totalEqualsSumOfAllVatInclusiveLinePrices() {
        Product apple = new Product("Apple", new Money(400), new VAT12());
        Product book = new Product("Book", new Money(2_000), new VAT6());
        cart.addItem(apple);
        cart.addItem(apple);
        cart.addItem(book);

        assertEquals(
                "Apple 2 896\nBook 1 2120\nTotal price: 3016",
                new Receipt(cart).getReceipt());
    }

    @Test
    void receiptHasOneLinePerProductAndNoTrailingNewline() {
        cart.addItem(new Product("Tea", new Money(100), new VAT0()));
        cart.addItem(new Product("Coffee", new Money(200), new VAT0()));

        assertEquals("Coffee 1 200\nTea 1 100\nTotal price: 300",
                new Receipt(cart).getReceipt());
    }

    @Test
    void fractionalVatInOreIsRounded() {
        cart.addItem(new Product("Pencil", new Money(99), new VAT25()));

        // Money.percentage() is used: 99 * 25 / 100 = 24.75 ore, rounded to 25 ore.
        assertEquals("Pencil 1 124\nTotal price: 124", new Receipt(cart).getReceipt());
    }
}
