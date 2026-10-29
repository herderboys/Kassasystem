package checkout;

public class Gtin {
    private final String code;
    private static final int EVEN_POSITION_WEIGHT = 1;
    private static final int ODD_POSITION_WEIGHT = 3;
    private static final int CHECKDIGIT_INDEX = 12;
    private static final int GTIN13_LENGTH = 13;

    // Only GTIN 13 is accepted
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

        if (code.length() != GTIN13_LENGTH) {
            throw new IllegalArgumentException("GTIN code must have a length of " + GTIN13_LENGTH + ".");
        }

        int expectedCheckDigit = calculateCheckDigit(code);
        if (Character.getNumericValue(code.charAt(CHECKDIGIT_INDEX)) != expectedCheckDigit) {
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

    // helper method to calculate check digit
    private static int calculateCheckDigit(String code) {
        /*
         * loops through and multiplies each number with a weight,
         * puts in a sum, calculates what the check digit should be
         */
        int sum = 0;
        for (int i = 0; i < CHECKDIGIT_INDEX; i++) {
            if (i % 2 == 0) {
                sum += (Character.getNumericValue(code.charAt(i)) * EVEN_POSITION_WEIGHT);
            } else {
                sum += (Character.getNumericValue(code.charAt(i)) * ODD_POSITION_WEIGHT);
            }
        }
        int expectedCheckDigit = (10 - sum % 10) % 10;
        return expectedCheckDigit;
    }
}
