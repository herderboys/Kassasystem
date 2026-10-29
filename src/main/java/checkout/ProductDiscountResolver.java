package checkout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDiscountResolver {

    // Behåller det gamla beteendet för befintliga anrop och tester.
    public Money getNewPrice(Product product, int quantity) {
        return calculatePrice(product, quantity, null, false);
    } 

    // Ny metod som tar hänsyn till kundens status.
    public Money getNewPrice(
            Product product, int quantity, Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException(
                "Customer cannot be null"
            );
        }

        return calculatePrice(product, quantity, customer, true);
    }

    private Money calculatePrice(
            Product product,
            int quantity,
            Customer customer,
            boolean checkCustomerStatus) {

        if (product == null) {
            throw new IllegalArgumentException(
                "Product cannot be null"
            );
        }

        if (quantity < 0) {
            throw new IllegalArgumentException(
                "Quantity cannot be negative"
            );
        }

        if (quantity == 0) {
            return new Money(0);
        }

        List<FixedDiscount> fixedDiscounts = new ArrayList<>();
        List<PercentageDiscount> percentageDiscounts =
            new ArrayList<>();

        MultiBuyDiscount bestMultiBuy = null;
        MemberDiscount member = null;
        StudentSeniorDiscount student = null;

        ProductGroup group = product.getProductGroup();
        Money unitPrice = product.getPrice();

        if (group == null) {
            return multiply(unitPrice, quantity);
        }

        for (Discount discount : group.getDiscounts()) {
            if (!discount.isValid()) {
                continue;
            }

            if (discount instanceof MultiBuyDiscount multiBuy) {
                if (quantity >= multiBuy.getQuantityRequired()
                        && (bestMultiBuy == null
                        || isBetterMultiBuy(multiBuy, bestMultiBuy))) {
                    bestMultiBuy = multiBuy;
                }
            } else if (discount instanceof FixedDiscount fixed) {
                fixedDiscounts.add(fixed);
            } else if (discount instanceof MemberDiscount memberDiscount) {
                member = memberDiscount;
            } else if (discount instanceof StudentSeniorDiscount studentDiscount) {
                student = studentDiscount;
            } else if (discount instanceof PercentageDiscount percentage) {
                percentageDiscounts.add(percentage);
            }
        }

        // Multibuy kan inte kombineras med andra rabatter.
        if (bestMultiBuy != null) {
            int required = bestMultiBuy.getQuantityRequired();
            int toPay = bestMultiBuy.getQuantityToPay();

            int groups = quantity / required;
            int remainder = quantity % required;
            int payableQuantity = groups * toPay + remainder;

            return multiply(product.getPrice(), payableQuantity);
        }

        // Fasta rabatter tillämpas först.
        for (FixedDiscount discount : fixedDiscounts) {
            unitPrice = applyFixedDiscount(unitPrice, discount);
        }

        boolean validMember = false;
        boolean studentOrSenior = false;

        if (checkCustomerStatus) {
            validMember = customer.hasValidMembershipOn(LocalDate.now());
            studentOrSenior = customer.getStudentSenior();
        }

        // Välj medlems- eller studentrabatt.
        if (member != null
                && (!checkCustomerStatus || validMember)) {
            unitPrice = applyPercentageDiscount(unitPrice, member);
        } else if (student != null
                && (!checkCustomerStatus
                    || (!validMember && studentOrSenior))) {
            unitPrice = applyPercentageDiscount(unitPrice, student);
        }

        // Övriga procentuella rabatter, exempelvis kuponger.
        for (PercentageDiscount discount : percentageDiscounts) {
            if (discount instanceof MemberDiscount
                    || discount instanceof StudentSeniorDiscount) {
                continue;
            }

            unitPrice = applyPercentageDiscount(unitPrice, discount);
        }

        return multiply(unitPrice, quantity);
    }

    private Money applyFixedDiscount(
            Money price, FixedDiscount discount) {

        int remaining = Math.max(
            0,
            price.getAmountInOre()
                - discount.getDiscountAmount().getAmountInOre()
        );

        return new Money(remaining);
    }

    private Money applyPercentageDiscount(
            Money price, PercentageDiscount discount) {

        return price.percentage(
            100 - discount.getDiscountPercentage()
        );
    }

    private Money multiply(Money price, int quantity) {
        return new Money(
            Math.multiplyExact(
                price.getAmountInOre(), quantity
            )
        );
    }

    private boolean isBetterMultiBuy(
            MultiBuyDiscount candidate,
            MultiBuyDiscount current) {

        long candidateSaving =
            candidate.getQuantityRequired()
                - candidate.getQuantityToPay();

        long currentSaving =
            current.getQuantityRequired()
                - current.getQuantityToPay();

        return candidateSaving * current.getQuantityRequired()
            > currentSaving * candidate.getQuantityRequired();
    }
}