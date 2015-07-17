package de.fhkoeln.exceptions;

/**
 * Defines an <code>Exception</code> that can occur while processing images.
 * @author Sascha Lemke
 */
public class ImageProcessingException extends PipelineException {

    /**
     * {@link java.lang.Exception#Exception}
     * @param message The error message.
     */
    public ImageProcessingException(String message) {
        super(message);
    }

    /**
     * {@link java.lang.Exception#Exception}
     * @param message The error message.
     * @param throwable The cause of the <code>Exception</code>.
     */
    public ImageProcessingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
