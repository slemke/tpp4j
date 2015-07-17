package de.fhkoeln.io;

import de.fhkoeln.datatypes.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class provides methods to read images from the file system.
 * The images use a special container class that can be used within the pipeline.
 * @author Sascha Lemke
 */
public class ImageReader implements DocumentReader<Image> {


    /**
     * {@link de.fhkoeln.io.DocumentReader#read}
     * @param file The <code>File</code> containing the data.
     * @return The <code>Image</code> from the file system.
     * @throws IOException
     */
    @Override
    public Image read(File file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        return new Image(file, bufferedImage);
    }

    /**
     * {@link de.fhkoeln.io.DocumentReader#read}
     * @param path The <code>File</code> containing the data.
     * @return The <code>Image</code> from the file system.
     * @throws IOException
     */
    @Override
    public Image read(String path) throws IOException {
        File file = new File(path);
        BufferedImage bufferedImage = ImageIO.read(file);
        return new Image(file, bufferedImage);
    }
}
