package de.fhkoeln.datatypes;

/**
 * Container class that stores data that is used in replacements like the <code>AbbreviationFilter</code> or <code>TextCleaningTask</code>.
 * @author Sascha Lemke
 */
public class Replacement {

    private String value;
    private String replacement;

    /**
     * Creates a <code>Replacement</code> instance by providing a value and a replacement.
     * @param value
     * @param replacement
     */
    public Replacement(String value, String replacement) {
        this.value = value;
        this.replacement = replacement;
    }

    /**
     * Returns the to-be-replaced value.
     * @return The value that is going to be replaced.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the replacement for the inital value.
     * @return The replacement.
     */
    public String getReplacement() {
        return replacement;
    }

    @Override
    public String toString() {
        return "Abbreviation{" +
                "value='" + value + '\'' +
                ", replacement='" + replacement + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Replacement that = (Replacement) o;

        if (!value.equals(that.value)) return false;
        if (replacement != null ? !replacement.equals(that.replacement) : that.replacement != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + (replacement != null ? replacement.hashCode() : 0);
        return result;
    }
}
