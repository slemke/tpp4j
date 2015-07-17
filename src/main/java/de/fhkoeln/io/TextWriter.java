package de.fhkoeln.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides methods to write text to the file system.
 * @author Sascha Lemke
 */
public class TextWriter implements DocumentWriter<String> {

    private static final Logger log = Logger.getLogger(TextWriter.class.getName());

    /**
     * {@link de.fhkoeln.io.DocumentWriter#write}
     * @param document The <code>Document</code>.
     * @param path The location as a <code>String</code> of the <code>Document</code>.
     * @throws IOException
     */
    @Override
    public void write(String document, String path) throws IOException {
        File file = new File(path);
        try {
            if(file.exists())
                file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Couldn't write text file: " + file.getName(), e);
        }
    }

    /**
     * {@link de.fhkoeln.io.DocumentWriter#write}
     * @param document The <code>Document</code>.
     * @param file The location as a <code>File</code> of the <code>Document</code>.
     * @throws IOException
     */
    @Override
    public void write(String document, File file) throws IOException {
        try {
            if(file.exists())
                file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Couldn't write text file: " + file.getName(), e);
        }
    }

}
