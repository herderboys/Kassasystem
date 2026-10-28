package checkout;

public abstract class VAT {
    private final int vatRate;

    public VAT(int vatRate) {
        this.vatRate = vatRate;
    }

    public int getVatRate() {
        return vatRate;
    }
}
