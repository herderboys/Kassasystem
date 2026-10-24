package checkout;

public class VatCategory {
    private final int vatRate;
    
    public VatCategory(int vatRate) {
        this.vatRate = vatRate;
    }

    public int getVatRate() {
        return vatRate;
    }

    public Money calculateVatFromNetPrice(Money priceExcludingVat) {
        int vatInOre = priceExcludingVat.getAmountInOre() * vatRate / 100;
        return new Money(vatInOre);
    }
}
