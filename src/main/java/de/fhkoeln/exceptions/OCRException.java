package de.fhkoeln.exceptions;

/**
 * Defines an <code>Exception</code> that can occur while interpreting text with Optical Character Recognition.
 * @author Sascha Lemke
 */
public class OCRException extends PipelineException {

    /**
     * {@link: java.lang.Exception#Exception}
     * @param message The error message.
     */
    public OCRException(String message) {
        super(message);
    }

    /**
     * {@link: java.lang.Exception#Exception}
     * @param message The error message.
     * @param throwable The cause of the <code>Exception</code>.
     */
    public OCRException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
