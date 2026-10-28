package checkout;

public abstract class VAT {
    private final int vatRate;

    public VAT(int vatRate) {
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
