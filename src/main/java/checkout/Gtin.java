package checkout;

public class Gtin {
    private String code;

    public Gtin(String code) {
        // replacing whitespace (//s) and dashes with an empty string, no char
        code = code.replaceAll("[\\s-]", "");
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
