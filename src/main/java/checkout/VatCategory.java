package checkout;

public class VatCategory {
    private final int vatRate;
    
    public VatCategory(int vatRate) {
        if (vatRate < 0) {
            throw new IllegalArgumentException("VAT rate cannot be negative");
        }
        this.vatRate = vatRate;
    }

    public int getVatRate() {
        return vatRate;
    }

    public Money calculateVatFromNetPrice(Money priceExcludingVat) {
        if (priceExcludingVat == null) {
            throw new IllegalArgumentException("Price excluding VAT cannot be null");
        }
        int vatInOre = priceExcludingVat.getAmountInOre() * vatRate / 100;
        return new Money(vatInOre);
    }
}
