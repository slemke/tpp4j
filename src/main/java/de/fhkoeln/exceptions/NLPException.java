package de.fhkoeln.exceptions;

/**
 * Defines an <code>Exception</code> that is used for NLP Tasks like Tokenizing or Part-of-Speech Tagging.
 * @author Sascha Lemke
 */
public class NLPException extends PipelineException {

    /**
     * {@link java.lang.Exception#Exception}
     * @param message The error message.
     */
    public NLPException(String message) {
        super(message);
    }

    /**
     * {@link java.lang.Exception#Exception}
     * @param message The error message.
     * @param throwable The cause of the <code>Exception</code>.
     */
    public NLPException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
