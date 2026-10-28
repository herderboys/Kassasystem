package checkout;

import java.util.ArrayList;
import java.util.List;

public class ProductDiscountResolver {

        
    public Money getNewPrice(Product product, int quantity) {
	
		List<FixedDiscount> fixedDiscounts = new ArrayList<>();
        List<PercentageDiscount> percentageDiscounts = new ArrayList<>();

        MultiBuyDiscount bestMultiBuy = null;
        MemberDiscount member = null;
        StudentSeniorDiscount student = null;
        

    	if (quantity < 0) {
            throw new IllegalArgumentException(
                "Quantity cannot be negative"
            );
        }

        if (quantity == 0) {
        	return new Money(0);
        }

        ProductGroup group = product.getProductGroup();
        Money unitPrice = product.getPrice();

        if (group == null) {
            return multiply(unitPrice, quantity);
        }


        for (Discount discount : group.getDiscounts()) {
            if (!discount.isValid()) {
                continue;
            }

            if (discount instanceof MultiBuyDiscount multiBuyDiscount) {
                if (quantity >= multiBuyDiscount.getQuantityRequired()
                 && (bestMultiBuy == null || isBetterMultiBuy(multiBuyDiscount, bestMultiBuy))) {
                    bestMultiBuy = multiBuyDiscount;
                }
            } else if (discount instanceof FixedDiscount d) {
                fixedDiscounts.add(d);
            } else if (discount instanceof MemberDiscount d) {
                member = d;
            } else if (discount instanceof StudentSeniorDiscount d) {
                student = d;
            } else if (discount instanceof PercentageDiscount d) {
                percentageDiscounts.add(d);
            }
        }

        // MultiBuy kan inte kombineras med andra rabatter.
        if (bestMultiBuy != null) {
            int required = bestMultiBuy.getQuantityRequired();
            int toPay = bestMultiBuy.getQuantityToPay();

            int groups = quantity / required;
            int remainder = quantity % required;
            int payableQuantity = groups * toPay + remainder;

            return multiply(product.getPrice(), payableQuantity);
        }

        // Fasta rabatter först.
        for (FixedDiscount discount : fixedDiscounts) {
            unitPrice = applyFixedDiscount(unitPrice, discount);
        }

        // Member slår ut Student/Senior.
        if (member != null) {
            unitPrice = applyPercentageDiscount(unitPrice, member);
        } else if (student != null) {
            unitPrice = applyPercentageDiscount(unitPrice, student);
        }

        // Övriga procentuella rabatter, exempelvis kuponger.
        for (PercentageDiscount discount : percentageDiscounts) {
            if (discount instanceof MemberDiscount|| discount instanceof StudentSeniorDiscount) {
                continue;
            }

            unitPrice = applyPercentageDiscount(unitPrice, discount);
        }

        return multiply(unitPrice, quantity);
    }

    private Money applyFixedDiscount(
            Money price, FixedDiscount discount) {
    	int remaining = Math.max(0,price.getAmountInOre()- discount.getDiscountAmount().getAmountInOre()
        );

        return new Money(remaining);
    }

    private Money applyPercentageDiscount(Money price, PercentageDiscount discount) {
        return price.percentage(100 - discount.getDiscountPercentage()
        );
    }

    private Money multiply(Money price, int quantity) {
        return new Money(
            Math.multiplyExact(price.getAmountInOre(), quantity)
        );
    }

    private boolean isBetterMultiBuy(MultiBuyDiscount candidate, MultiBuyDiscount current) {

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