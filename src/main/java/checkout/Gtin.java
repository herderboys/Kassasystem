package checkout;

public class Gtin {
    private final String code;

    public Gtin(String code) {
        if (code == null) {
            throw new IllegalArgumentException("GTIN code cannot be null.");
        }

        // replacing whitespace (\\s) and dashes with an empty string, no char
        code = code.replaceAll("[\\s-]", "");

        if (code.isBlank()) {
            throw new IllegalArgumentException("GTIN code cannot be blank.");
        }

        // checks if something is not a digit (\\d)
        if (!code.matches("\\d+")) {
            throw new IllegalArgumentException("GTIN code must contain only digits.");
        }

        if (code.length() != 13) {
            throw new IllegalArgumentException("GTIN code must have a length of 13.");
        }

        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
