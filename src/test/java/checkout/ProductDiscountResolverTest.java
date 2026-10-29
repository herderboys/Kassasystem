package checkout;


import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

class ProductDiscountResolverTest {

	private static final ZoneId ZONE = ZoneId.of("Europe/Stockholm");

	private static final Clock CLOCK = Clock.fixed(Instant.parse("2026-10-23T10:00:00Z"), ZONE);

	private static final LocalDateTime START = LocalDateTime.of(2026, 10, 23, 11, 0);

	private static final LocalDateTime END = LocalDateTime.of(2029, 10, 23, 13, 0);

	// Hjälpmetoder för rabatter

	private PercentageDiscount createPercentageDiscount(String name, int discountNumber) {

		return new PercentageDiscount(name, discountNumber, START, END, CLOCK);
	}

	private MultiBuyDiscount createMultiBuyDiscount(String name, int quantityRequired, int quantityToPay) {

		return new MultiBuyDiscount(name, quantityRequired, quantityToPay, START, END, CLOCK);
	}

	private FixedDiscount createFixedDiscount(String name, Money discountAmount) {

		return new FixedDiscount(name, discountAmount, START, END, CLOCK);
	}

	private MemberDiscount createMemberDiscount(String name) {
		return new MemberDiscount(name, START, END, CLOCK);
	}

	private StudentSeniorDiscount createStudentSeniorDiscount(String name) {

		return new StudentSeniorDiscount(name, START, END, CLOCK);
	}

	// Hjälpmetoder för kunder

	private Customer createRegularCustomer() {
		return new Customer("Regular", new Money(10000));
	}

	private Customer createMemberCustomer() {
		Customer customer = createRegularCustomer();

		customer.setMembership(new Membership(LocalDate.of(2029, 12, 31)));

		return customer;
	}

	private Customer createStudentCustomer() {
		Customer customer = createRegularCustomer();
		customer.setStudentSenior(true);

		return customer;
	}

	@Test
	void nullProductShouldThrowException() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> resolver.getNewPrice(null, 1)
	    );
	}
	
	@Test
	void nullCustomerShouldThrowException() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(20));

	    assertThrows(
	        IllegalArgumentException.class,
	        () -> resolver.getNewPrice(product, 1, null)
	    );
	}
	
	@Test
	void regularPercentageDiscountShouldReachPercentageDiscountBranch() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(100));
	    ProductGroup group = new ProductGroup("group");
	    Customer customer = createRegularCustomer();

	    group.addProduct(product);
	    group.addDiscount(createPercentageDiscount("Coupon", 25));

	    assertEquals(
	        new Money(75),
	        resolver.getNewPrice(product, 1, customer)
	    );
	}
	
	@Test
	void customerWithoutMembershipOrStudentStatusGetsNoSpecialDiscount() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(100));
	    ProductGroup group = new ProductGroup("group");
	    Customer customer = new Customer("Test", new Money(1000));

	    group.addProduct(product);
	    group.addDiscount(createMemberDiscount("Member"));
	    group.addDiscount(createStudentSeniorDiscount("Student"));

	    assertEquals(
	        new Money(100),
	        resolver.getNewPrice(product, 1, customer)
	    );
	}
	
	@Test
	void validMemberGetsMemberDiscount() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(100));
	    ProductGroup group = new ProductGroup("group");
	    Customer customer = new Customer("Member", new Money(1000));

	    customer.setMembership(
	        new Membership(LocalDate.now().plusDays(30))
	    );

	    group.addProduct(product);
	    group.addDiscount(createMemberDiscount("Member discount"));
	    group.addDiscount(createStudentSeniorDiscount("Student discount"));

	    assertEquals(
	        new Money(80),
	        resolver.getNewPrice(product, 1, customer)
	    );
	}
	
	@Test
	void priceWithoutCustomerDoesNotCheckCustomerStatus() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(100));

	    assertEquals(new Money(100), resolver.getNewPrice(product, 1));
	}
	
	@Test
	void studentSeniorDiscountAppliesWhenCustomerHasNoMembership() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(100));
	    ProductGroup group = new ProductGroup("group");
	    Customer customer = new Customer("Student", new Money(1000));

	    customer.setStudentSenior(true);

	    group.addProduct(product);
	    group.addDiscount(createMemberDiscount("Member discount"));
	    group.addDiscount(createStudentSeniorDiscount("Student discount"));

	    assertEquals(
	        new Money(90),
	        resolver.getNewPrice(product, 1, customer)
	    );
	}
	
	@Test
	void ordinaryPercentageDiscountShouldBeAddedToPercentageDiscounts() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(100));
	    ProductGroup group = new ProductGroup("group");

	    group.addProduct(product);
	    group.addDiscount(createPercentageDiscount("Coupon", 25));

	    Money result = resolver.getNewPrice(product, 1, createRegularCustomer());

	    assertEquals(new Money(75), result);
	}
	
	@Test
	void studentCustomerWithoutStudentDiscountPaysFullPrice() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(100));
	    ProductGroup group = new ProductGroup("group");

	    group.addProduct(product);
	    group.addDiscount(createPercentageDiscount("Coupon", 10));

	    Customer customer = createStudentCustomer();

	    assertEquals(
	        new Money(90),
	        resolver.getNewPrice(product, 1, customer)
	    );
	}
	
	@Test
	void studentSeniorCustomerGetsStudentDiscount() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(100));
	    ProductGroup group = new ProductGroup("group");
	    Customer customer = new Customer("Student", new Money(1000));

	    customer.setStudentSenior(true);

	    group.addProduct(product);
	    group.addDiscount(createMemberDiscount("Member discount"));
	    group.addDiscount(createStudentSeniorDiscount("Student discount"));

	    assertEquals(
	        new Money(90),
	        resolver.getNewPrice(product, 1, customer)
	    );
	}
	
	@Test
	void ordinaryPercentageDiscountShouldBeApplied() {
	    ProductDiscountResolver resolver = new ProductDiscountResolver();
	    Product product = new Product("Milk", new Money(100));
	    ProductGroup group = new ProductGroup("group");

	    group.addProduct(product);
	    group.addDiscount(createPercentageDiscount("Coupon", 25));

	    assertEquals(new Money(75), resolver.getNewPrice(product, 1));
	}

	@Test
	void percentageDiscountShouldReducePrice() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createPercentageDiscount("50 off", 50));

		assertEquals(new Money(10), resolver.getNewPrice(product, 1, createRegularCustomer()));
	}

	@Test
	void zeroQuantityShouldReturnZero() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));

		assertEquals(new Money(0), resolver.getNewPrice(product, 0, createRegularCustomer()));
	}

	@Test
	void negativeQuantityShouldThrowException() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));

		assertThrows(IllegalArgumentException.class, () -> resolver.getNewPrice(product, -1, createRegularCustomer()));
	}

	@Test
	void productWithoutGroupShouldHaveNormalPrice() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));

		assertEquals(new Money(60), resolver.getNewPrice(product, 3, createRegularCustomer()));
	}

	// 2. Fasta rabatter

	@Test
	void fixedDiscountShouldReducePrice() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createFixedDiscount("5 off", new Money(5)));

		assertEquals(new Money(15), resolver.getNewPrice(product, 1, createRegularCustomer()));
	}

	@Test
	void fixedDiscountShouldNotMakePriceNegative() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createFixedDiscount("30 off", new Money(30)));

		assertEquals(new Money(0), resolver.getNewPrice(product, 1, createRegularCustomer()));
	}

	@Test
	void multipleFixedDiscountsShouldBeAppliedInSequence() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(100));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createFixedDiscount("10 off", new Money(10)));
		group.addDiscount(createFixedDiscount("20 off", new Money(20)));

		assertEquals(new Money(70), resolver.getNewPrice(product, 1, createRegularCustomer()));
	}

	// 3. Procentrabatter

	@Test
	void percentageDiscountShouldApplyToMultipleItems() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createPercentageDiscount("50 off", 50));

		assertEquals(new Money(30), resolver.getNewPrice(product, 3, createRegularCustomer()));
	}

	@Test
	void fixedDiscountShouldBeAppliedBeforePercentageDiscount() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(100));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createFixedDiscount("10 off", new Money(10)));
		group.addDiscount(createPercentageDiscount("50 percent off", 50));

		assertEquals(new Money(45), resolver.getNewPrice(product, 1, createRegularCustomer()));
	}

	@Test
	void memberDiscountShouldCombineWithOtherPercentageDiscount() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(100));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMemberDiscount("Member"));
		group.addDiscount(createPercentageDiscount("Coupon", 10));

		assertEquals(new Money(72), resolver.getNewPrice(product, 1, createMemberCustomer()));
	}

	// 4. Multibuy-rabatter

	@Test
	void multiBuyDiscountShouldChargeForPayableQuantity() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMultiBuyDiscount("3 for 2", 3, 2));

		assertEquals(new Money(40), resolver.getNewPrice(product, 3, createRegularCustomer()));
	}

	@Test
	void multiBuyDiscountShouldChargeFullPriceForRemainder() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMultiBuyDiscount("3 for 2", 3, 2));

		assertEquals(new Money(60), resolver.getNewPrice(product, 4, createRegularCustomer()));
	}

	@Test
	void multiBuyDiscountShouldTakePriorityOverPercentageDiscount() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMultiBuyDiscount("3 for 2", 3, 2));
		group.addDiscount(createPercentageDiscount("50 off", 50));

		assertEquals(new Money(40), resolver.getNewPrice(product, 3, createRegularCustomer()));
	}

	@Test
	void multiBuyDiscountShouldNotApplyBelowRequiredQuantity() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMultiBuyDiscount("3 for 2", 3, 2));

		assertEquals(new Money(40), resolver.getNewPrice(product, 2, createRegularCustomer()));
	}

	@Test
	void bestMultiBuyDiscountShouldBeSelected() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMultiBuyDiscount("3 for 2", 3, 2));
		group.addDiscount(createMultiBuyDiscount("4 for 2", 4, 2));

		assertEquals(new Money(40), resolver.getNewPrice(product, 4, createRegularCustomer()));
	}

	@Test
	void equalMultiBuySavingsShouldKeepFirstDiscount() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMultiBuyDiscount("3 for 2", 3, 2));
		group.addDiscount(createMultiBuyDiscount("4 for 3", 4, 3));

		assertEquals(new Money(60), resolver.getNewPrice(product, 4, createRegularCustomer()));
	}

	// 5. Medlemsrabatt och student-/seniorrabatt

	@Test
	void memberDiscountShouldApplyToMember() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(100));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMemberDiscount("Member"));

		assertEquals(new Money(80), resolver.getNewPrice(product, 1, createMemberCustomer()));
	}

	@Test
	void studentSeniorDiscountShouldApplyToEligibleCustomer() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(100));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createStudentSeniorDiscount("Student"));

		assertEquals(new Money(90), resolver.getNewPrice(product, 1, createStudentCustomer()));
	}

	@Test
	void regularCustomerShouldNotReceiveMemberOrStudentDiscount() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(100));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMemberDiscount("Member"));
		group.addDiscount(createStudentSeniorDiscount("Student"));

		assertEquals(new Money(100), resolver.getNewPrice(product, 1, createRegularCustomer()));
	}

	@Test
	void memberDiscountShouldTakePriorityOverStudentSeniorDiscount() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(100));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMemberDiscount("Member"));
		group.addDiscount(createStudentSeniorDiscount("Student"));

		// Kunden är både medlem och student/senior.
		assertEquals(new Money(80), resolver.getNewPrice(product, 1, createMemberAndStudentCustomer()));
	}

	private Customer createMemberAndStudentCustomer() {
		Customer customer = createMemberCustomer();
		customer.setStudentSenior(true);
		return customer;
	}

	@Test
	void expiredMembershipShouldNotReceiveMemberDiscount() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(100));
		ProductGroup group = new ProductGroup("group");

		group.addProduct(product);
		group.addDiscount(createMemberDiscount("Member"));

		Customer customer = createRegularCustomer();
		customer.setMembership(new Membership(LocalDate.of(2020, 1, 1)));

		assertEquals(new Money(100), resolver.getNewPrice(product, 1, customer));
	}

	// 6. Ogiltiga rabatter

	@Test
	void invalidPercentageDiscountShouldBeIgnored() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(100));
		ProductGroup group = new ProductGroup("group");

		PercentageDiscount discount = new PercentageDiscount("Future discount", 50,
				LocalDateTime.of(2026, 10, 23, 13, 0), END, CLOCK);

		group.addProduct(product);
		group.addDiscount(discount);

		assertEquals(new Money(100), resolver.getNewPrice(product, 1, createRegularCustomer()));
	}

	@Test
	void invalidMultiBuyDiscountShouldBeIgnored() {
		ProductDiscountResolver resolver = new ProductDiscountResolver();
		Product product = new Product("Milk", new Money(20));
		ProductGroup group = new ProductGroup("group");

		MultiBuyDiscount discount = new MultiBuyDiscount("Expired offer", 3, 2, LocalDateTime.of(2020, 1, 1, 0, 0),
				LocalDateTime.of(2025, 1, 1, 0, 0), CLOCK);

		group.addProduct(product);
		group.addDiscount(discount);

		assertEquals(new Money(60), resolver.getNewPrice(product, 3, createRegularCustomer()));
	}
}
