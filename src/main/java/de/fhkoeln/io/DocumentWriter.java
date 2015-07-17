package de.fhkoeln.io;

import java.io.File;
import java.io.IOException;

/**
 * This interface defines consistent methods for a writing documents to the file system.
 * @param <T> The typ that is represents that data that is going to be written to the file system.
 * @author Sascha Lemke
 */
public interface DocumentWriter<T> {

    /**
     * Writes the <code>Document</code> object to the file system.
     * @param document The <code>Document</code>.
     * @param path The location as a <code>String</code> of the <code>Document</code>.
     * @throws IOException
     */
    public void write(T document, String path) throws IOException;

    /**
     * Writes the <code>Document</code> object to the file system.
     * @param document The <code>Document</code>.
     * @param file The location as a <code>File</code> of the <code>Document</code>.
     * @throws IOException
     */
    public void write(T document, File file) throws IOException;
}
