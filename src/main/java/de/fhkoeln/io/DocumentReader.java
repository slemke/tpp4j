package de.fhkoeln.io;

import java.io.File;
import java.io.IOException;

/**
 * This interface defines consistent methods for a accessing documents used within the pipeline.
 * @param <T> The typ that is used to represent data from the files.
 * @author Sascha Lemke
 */
public interface DocumentReader<T> {

    /**
     * Reads the file and returns a <code>Document</code> by using a <code>File</code> object.
     * @param file The file containing the data.
     * @return The <code>Document</code> from the directory.
     * @throws IOException
     */
    public T read(File file) throws IOException;

    /**
     * Reads the file and returns a <code>Document</code> by using a <code>String</code> that provides the file location.
     * @param path The file containing the data.
     * @return The <code>Document</code> from the directory.
     * @throws IOException
     */
    public T read(String path) throws IOException;
}
