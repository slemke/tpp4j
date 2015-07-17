package de.fhkoeln.io;

import de.fhkoeln.datatypes.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * This class provides methods to write images to the file system.
 * The images use a special container class that can be used within the pipeline.
 * @author Sascha Lemke
 */
public class ImageWriter implements DocumentWriter<Image> {

    /**
     * {@link de.fhkoeln.io.DocumentWriter#write}
     * @param document The <code>Image</code> that is going to be written to the file system.
     * @param path The location as a <code>String</code> of the <code>Document</code>.
     * @throws IOException
     */
    @Override
    public void write(Image document, String path) throws IOException {
        ImageIO.write(document.getBuffer(), document.getType().substring(1), new File(path));
    }

    /**
     * {@link de.fhkoeln.io.DocumentWriter#write}
     * @param document The <code>Image</code> that is going to be written to the file system.
     * @param file The location as a <code>File</code> of the <code>Document</code>.
     * @throws IOException
     */
    @Override
    public void write(Image document, File file) throws IOException {
        ImageIO.write(document.getBuffer(), document.getType().substring(1), file);
    }

}
