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

        /*
         * loops through and multiplies each number with a weight,
         * puts in a sum, checks if last digit if correct according
         * to GTIN standard
         */
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                sum += (Character.getNumericValue(code.charAt(i)) * 1);
            } else {
                sum += (Character.getNumericValue(code.charAt(i)) * 3);
            }
        }

        int expectedCheckDigit = (10 - sum % 10) % 10;

        if (Character.getNumericValue(code.charAt(12)) != expectedCheckDigit) {
            throw new IllegalArgumentException("GTIN code has wrong check digit.");
        }

        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override 
    public boolean equals(Object other) {
        if (other instanceof Gtin g) {
            return code.equals(g.getCode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
