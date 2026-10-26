package checkout;

public class Gtin {
    private String code;

    public Gtin(String code) {
        // replacing whitespace (//s) and dashes with an empty string, no char
        if (code == null) {
            throw new IllegalArgumentException("GTIN code cannot be null.");
        }
        code = code.replaceAll("[\\s-]", "");
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
