package de.fhkoeln.datatypes;

import java.util.ArrayList;

/**
 * Defines a consistent class for <code>Text</code> representation used within the pipeline.
 * It also can hold different representations of the same text like a tokenized or stop word free version.
 * @author Sascha Lemke
 */
public class Text implements Document {

    private String text;
    private String name;
    private ArrayList<Token> stopWordFree;
    private ArrayList<Token> tokenized;
    private ArrayList<Image> imageList = new ArrayList<Image>();
    private ArrayList<Image> processedImages = new ArrayList<Image>();

    /**
     * Adds an <code>Image</code> to the text document that will be used for OCR purposes. One image equals one page of text.
     * @param image The image containing the text.
     */
    public void addImage(Image image) {
        imageList.add(image);
    }

    /**
     * Removes an <code>Image</code> from the text document.
     * @param image The image that is going to be removed.
     */
    public void removeImage(Image image) {
        imageList.remove(image);
    }

    /**
     * Returns the number of <code>Image</code>s the text document currently provides and will be used for OCR purposes.
     * @return The number of <code>Image</code>s.
     */
    public int numberOfImages() {
        return imageList.size();
    }

    /**
     * Returns a list of all the <code>Image</code>s the text document contains and that are going to be used for OCR purposes.
     * @return A list of <code>Image</code>s that will be used for OCR purposes.
     */
    public ArrayList<Image> getImages() { return imageList; }

    /**
     * Returns a list of processed <code>Image</code>s.
     * @return A list of processed <code>Image</code>s.
     */
    public ArrayList<Image> getProcessedImages() {
        return this.processedImages;
    }

    /**
     * Assigns a list of processed <code>Image</code>s to the text.
     * @param images A list of processed <code>Image</code>s.
     */
    public void setProcessedImages(ArrayList<Image> images) {
        this.processedImages = images;
    }

    /**
     * Checks if the text already contains the provided <code>Image</code>.
     * @param image The <code>Image</code> in question
     * @return
     */
    public boolean contains(Image image) {
        return imageList.contains(image);
    }

    /**
     * Returns the name of the text document, specified by the folder name.
     * @return The name of the text document.
     */
    public String getFileName() {
        return name;
    }

    /**
     * Sets the name of the document to the provided <code>String</code>.
     * @param name The new document name.
     */
    public void setFileName(String name) {
        this.name = name;
    }

    /**
     * Returns the text of the document. Returns <code>null</code> if no text is set.
     * @return The text of the document
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the document to the provided <code>String</code>.
     * @param text The new text of the document.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns a stop word free version of the text document. Returns <code>null</code> an empty list if no version is set.
     * @return The <code>Token</code> list of the stop word free text document.
     */
    public ArrayList<Token> getStopWordFreeText() {
        return stopWordFree;
    }

    /**
     * Assigns a list of <code>Token</code> as the stop word free version of the text document.
     * @param text The new stop word free version of the text document.
     */
    public void setStopWordFreeText(ArrayList<Token> text) {
        this.stopWordFree = text;
    }

    /**
     * Returns a tokenized version of the text. Returns <code>null</code> if none is set.
     * @return A list of <code>Token</code>.
     */
    public ArrayList<Token> getTokenizedText() {
        return tokenized;
    }

    /**
     * Assigns a list of <code>Token</code> as the tokenized version of the text document.
     * @param text A list of <code>Token</code>.
     */
    public void setTokenizedText(ArrayList<Token> text) {
        this.tokenized = text;
    }
}
