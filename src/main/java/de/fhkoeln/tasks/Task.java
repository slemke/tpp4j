package de.fhkoeln.tasks;

import de.fhkoeln.datatypes.Document;
import de.fhkoeln.exceptions.PipelineException;

import java.util.logging.Logger;

/**
 * An abstract class that acts as a template for new tasks used by the <code>Pipeline</code>.
 * @author Sascha Lemke
 */
public abstract class Task {

    private String directory;
    private static final Logger log = Logger.getLogger(ImageProcessingTask.class.getName());

    /**
     * This abstract method is used to edit the <code>Document</code> provided by the <code>Pipeline</code>.
     * @param document The <code>Document</code> that is going to be edited.
     * @return The edited <code>Document</code>.
     */
    public abstract Document apply(Document document) throws PipelineException;

    /**
     * Sets the working directory that the task uses. This shouldn't be used as the <code>Pipeline</code> overwrites the value every time the task is added to the <code>Pipeline</code>.
     * @param directory The path to the working directory.
     */
    public void setWorkingDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * Returns the path to the working directory.
     * @return The path to the working directory.
     */
    public String getWorkingDirectory() {
        return directory;
    }

    /**
     * Returns an instance of the <code>Logger</code>.
     * @return The <code>Logger</code> object
     */
    public Logger getLogger() {
        return log;
    }

}
