package checkout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClaudeReceiptTest {

    /**
     * Unit tests: Receipt in isolation, with ShoppingCart mocked.
     * Only formatting, sorting and the total are under test here; the
     * price/VAT values are whatever the mocked cart says they are.
     */
    @Nested
    @ExtendWith(MockitoExtension.class)
    @DisplayName("Receipt with a mocked ShoppingCart")
    class WithMockedCart {

        @Mock
        private ShoppingCart cart;

        // LinkedHashMap is a HashMap, so it can be returned from the cart,
        // and it lets the tests control iteration order.
        private final LinkedHashMap<Product, Integer> quantities = new LinkedHashMap<>();
        private final LinkedHashMap<Product, Integer> prices = new LinkedHashMap<>();

        private void stubCart(int totalInOre) {
            when(cart.getCart()).thenReturn(quantities);
            when(cart.getPriceList()).thenReturn(prices);
            when(cart.getPrice()).thenReturn(new Money(totalInOre));
        }

        private void addLine(String name, int quantity, int lineTotalInOre) {
            Product p = new Product(name, new Money(1));
            quantities.put(p, quantity);
            prices.put(p, lineTotalInOre);
        }

        @Test
        @DisplayName("empty cart gives only the total line")
        void emptyCart() {
            stubCart(0);

            assertEquals("Total price: 0", new Receipt(cart).getReceipt());
        }

        @Test
        @DisplayName("a line is formatted as \"<name> <quantity> <line price>\"")
        void singleLineFormat() {
            addLine("Milk", 2, 3000);
            stubCart(3000);

            assertEquals("""
                    Milk 2 3000
                    Total price: 3000""", new Receipt(cart).getReceipt());
        }

        @Test
        @DisplayName("lines are sorted alphabetically, whatever order the cart returns them in")
        void linesAreSorted() {
            addLine("Cheese", 1, 5000);
            addLine("Apple", 3, 1500);
            addLine("Bread", 1, 2500);
            stubCart(9000);

            assertEquals("""
                    Apple 3 1500
                    Bread 1 2500
                    Cheese 1 5000
                    Total price: 9000""", new Receipt(cart).getReceipt());
        }

        @Test
        @DisplayName("sorting is case-sensitive: uppercase names come before lowercase ones")
        void sortingIsCaseSensitive() {
            // Documents current behaviour of Collections.sort on Strings.
            addLine("apple", 1, 100);
            addLine("Banana", 1, 200);
            stubCart(300);

            assertEquals("""
                    Banana 1 200
                    apple 1 100
                    Total price: 300""", new Receipt(cart).getReceipt());
        }

        @Test
        @DisplayName("the total is the one reported by the cart")
        void totalComesFromCart() {
            addLine("Milk", 1, 1250);
            stubCart(1250);

            String receipt = new Receipt(cart).getReceipt();

            assertTrue(receipt.endsWith("Total price: 1250"), receipt);
        }

        @Test
        @DisplayName("receipt does not end with a newline")
        void noTrailingNewline() {
            addLine("Milk", 1, 1250);
            stubCart(1250);

            assertFalse(new Receipt(cart).getReceipt().endsWith("\n"));
        }

        @Test
        @DisplayName("calling getReceipt twice gives the same result")
        void getReceiptIsRepeatable() {
            addLine("Milk", 1, 1250);
            addLine("Bread", 2, 5000);
            stubCart(6250);
            Receipt receipt = new Receipt(cart);

            assertEquals(receipt.getReceipt(), receipt.getReceipt());
        }
    }

    /**
     * Integration tests: Receipt built from a real ShoppingCart, so the
     * expected line prices include VAT as computed by ShoppingCart/VAT.
     */
    @Nested
    @DisplayName("Receipt with a real ShoppingCart")
    class WithRealCart {

        private ShoppingCart cart;

        @BeforeEach
        void setUp() {
            cart = new ShoppingCart(new Customer("Alex", new Money(100_000)));
        }

        @Test
        @DisplayName("default VAT is 25%")
        void defaultVatIs25Percent() {
            cart.addItem(new Product("Milk", new Money(1000)));

            assertEquals("""
                    Milk 1 1250
                    Total price: 1250""", new Receipt(cart).getReceipt());
        }

        static Stream<Arguments> vatRates() {
            return Stream.of(
                    Arguments.of(new VAT0(), 1000),
                    Arguments.of(new VAT6(), 1060),
                    Arguments.of(new VAT12(), 1120),
                    Arguments.of(new VAT25(), 1250));
        }

        @ParameterizedTest(name = "{0} rate on 1000 öre gives {1}")
        @MethodSource("vatRates")
        @DisplayName("line price includes the product's VAT")
        void linePriceIncludesVat(VAT vat, int expected) {
            cart.addItem(new Product("Item", new Money(1000), vat));

            assertEquals("Item 1 " + expected + "\nTotal price: " + expected,
                    new Receipt(cart).getReceipt());
        }

        @Test
        @DisplayName("VAT is rounded to the nearest ore")
        void vatIsRounded() {
            // 99 * 6 / 100 = 5.94 -> 6 öre VAT
            cart.addItem(new Product("Gum", new Money(99), new VAT6()));

            assertEquals("""
                    Gum 1 105
                    Total price: 105""", new Receipt(cart).getReceipt());
        }

        @Test
        @DisplayName("adding the same product twice gives one line with quantity 2")
        void sameProductIsGrouped() {
            Product milk = new Product("Milk", new Money(1000));
            cart.addItem(milk);
            cart.addItem(milk);

            assertEquals("""
                    Milk 2 2500
                    Total price: 2500""", new Receipt(cart).getReceipt());
        }

        @Test
        @DisplayName("removed items are not on the receipt")
        void removedItemsAreLeftOut() {
            Product milk = new Product("Milk", new Money(1000));
            Product bread = new Product("Bread", new Money(2000));
            cart.addItem(milk);
            cart.addItem(milk);
            cart.addItem(bread);
            cart.removeItem(milk);
            cart.removeItem(bread);

            assertEquals("""
                    Milk 1 1250
                    Total price: 1250""", new Receipt(cart).getReceipt());
        }

        @Test
        @DisplayName("mixed cart: sorted lines and total equal to the sum of the lines")
        void mixedCart() {
            Product milk = new Product("Milk", new Money(1500), new VAT12());   // 1680 each
            Product apple = new Product("Apple", new Money(500), new VAT6());   // 3 x 500 = 1500 + 90
            Product book = new Product("Book", new Money(20000), new VAT0());   // 20000
            cart.addItem(milk);
            cart.addItem(apple);
            cart.addItem(book);
            cart.addItem(apple);
            cart.addItem(apple);

            assertEquals("""
                    Apple 3 1590
                    Book 1 20000
                    Milk 1 1680
                    Total price: 23270""", new Receipt(cart).getReceipt());
        }

        @Test
        @DisplayName("receipt is a snapshot: later changes to the cart don't affect it")
        void receiptIsUnaffectedByLaterCartChanges() {
            Product milk = new Product("Milk", new Money(1000));
            cart.addItem(milk);
            Receipt receipt = new Receipt(cart);
            String before = receipt.getReceipt();

            cart.addItem(milk);
            cart.addItem(new Product("Bread", new Money(2000)));

            assertEquals(before, receipt.getReceipt());
        }
    }
}
