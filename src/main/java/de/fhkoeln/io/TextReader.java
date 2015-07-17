package de.fhkoeln.io;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides methods to read text from the file system.
 * @author Sascha Lemke
 */
public class TextReader implements DocumentReader<String> {

    private static final Logger log = Logger.getLogger(TextReader.class.getName());

    /**
     * {@link de.fhkoeln.io.DocumentReader#read}
     * @param file The <code>File</code> containing the data.
     * @return The text from the file system.
     * @throws IOException
     */
    @Override
    public String read(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            FileReader reader = new FileReader(file);
            BufferedReader textReader = new BufferedReader(reader);

            while(( line = textReader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "Couldn't find file to read: " + file.getName(), e);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to read file: " + file.getName(), e);
        }
        return sb.toString();
    }

    /**
     * {@link de.fhkoeln.io.DocumentReader#read}
     * @param path The <code>File</code> containing the data.
     * @return The text from the file system.
     * @throws IOException
     */
    @Override
    public String read(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            FileReader reader = new FileReader(path);
            BufferedReader textReader = new BufferedReader(reader);

            while(( line = textReader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "Couldn't find file to read: " + path, e);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to read file: " + path, e);
        }
        return sb.toString();
    }

}
