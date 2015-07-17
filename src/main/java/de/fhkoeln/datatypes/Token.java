package de.fhkoeln.datatypes;

/**
 * Container class that stores information about a token.
 * @author Sascha Lemke
 */
public class Token {

    private String token;
    private String nerLabel;
    private String posTag;

    /**
     * Creates a <code>Token</code> instance.
     * @param token The <code>Token</code> to be stored.
     */
    public Token(String token) {
        this.token = token;
    }

    /**
     * Creates a <code>Token</code> instance with additional information such as the Named Entity Tag and the Part-of-Speech Tag.
     * @param token The <code>Token</code> to be stored.
     * @param nerLabel The Named Entity Tag of the <code>Token</code>.
     * @param posTag The Part-of-Speech Tag of the <code>Token</code>.
     */
    public Token(String token, String nerLabel, String posTag) {
        this.token = token;
        this.nerLabel = nerLabel;
        this.posTag = posTag;
    }

    /**
     * Returns the <code>Token</code> as a <code>String</code>.
     * @return The <code>Token</code>.
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the Named Entity Tag of the <code>Token</code>.
     * @return The Named Entity Tag of the <code>Token</code>.
     */
    public String getNerLabel() {
        return nerLabel;
    }

    /**
     * Assigns a Named Entity Tag to the <code>Token</code>.
     * @param nerLabel The Named Entity Tag.
     */
    public void setNerLabel(String nerLabel) {
        this.nerLabel = nerLabel;
    }

    /**
     * Returns the Part-of-Speech Tag of the <code>Token</code>.
     * @return The Part-of-Speech Tag of the <code>Token</code>.
     */
    public String getPosTag() {
        return posTag;
    }

    /**
     * Assigns a Part-of-Speech Tag to the <code>Token</code>.
     * @param posTag The Part-of-Speech Tag
     */
    public void setPosTag(String posTag) {
        this.posTag = posTag;
    }
}
