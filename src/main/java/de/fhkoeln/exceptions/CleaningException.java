package de.fhkoeln.exceptions;

/**
 * Defines an <code>Exception</code> that can occur while cleaning text.
 * @author Sascha Lemke
 */
public class CleaningException extends PipelineException {

    /**
     * {@link java.lang.Exception#Exception}
     * @param message The error message.
     */
    public CleaningException(String message) {
        super(message);
    }

    /**
     * {@link java.lang.Exception#Exception}
     * @param message The error message.
     * @param throwable The cause of the <code>Exception</code>.
     */
    public  CleaningException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
