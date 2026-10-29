package checkout;

public class Product {
    private final String name;
    private final Money price;
    private final VAT vat;
    private ProductGroup productGroup;


    public Product(String name, Money price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty or null.");
        }
        if (price == null) {
            throw new IllegalArgumentException("Money can't be null.");
        }

        this.name = name;
        this.price = price;
        vat = new VAT25();
    }

    public Product(String name, Money price, VAT vat) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty or null.");
        }
        if (price == null) {
            throw new IllegalArgumentException("Money can't be null.");
        }
        if (vat == null){
            throw new IllegalArgumentException("VAT can't be null.");
        }

        this.name = name;
        this.price = price;
        this.vat = vat;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public VAT getVAT() { return vat; }
    
    public ProductGroup getProductGroup() {
        return productGroup;
    }

    void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }
}
