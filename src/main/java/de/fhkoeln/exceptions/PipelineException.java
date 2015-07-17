package de.fhkoeln.exceptions;

/**
 * Defines the parent <code>Exception</code> for all <code>Exception</code>s used within the pipeline.
 * @author Sascha Lemke
 */
public class PipelineException extends Exception {

    /**
     * {@link java.lang.Exception#Exception}
     * @param message The error message.
     */
    public PipelineException(String message) {
        super(message);
    }

    /**
     * {@link java.lang.Exception#Exception}
     * @param message The error message.
     * @param throwable The cause of the <code>Exception</code>.
     */
    public PipelineException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
