package checkout;

public class VatCategory {
    private final int vatRate;
    
    public VatCategory(int vatRate) {
        this.vatRate = vatRate;
    }

    public int getVatRate() {
        return vatRate;
    }
}
