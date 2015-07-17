package de.fhkoeln.tests.io;

import de.fhkoeln.datatypes.Image;
import de.fhkoeln.io.ImageReader;
import de.fhkoeln.io.ImageWriter;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ImageWriterTests {

    @Test
    public void writeImageByString() throws IOException {
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, new Color(10, 50, 100).getRGB());
        ImageWriter writer = new ImageWriter();
        File file = new File("src/test/resources/images/write.jpg");
        Image image = new Image(file, bufferedImage);
        writer.write(image, "src/test/resources/images/write.jpg");
        ImageReader reader = new ImageReader();
        Image readImage = reader.read(file);
        readImage.load();
        BufferedImage readImageBuffer = readImage.getBuffer();
        Color color = new Color(readImageBuffer.getRGB(0, 0));
        assertEquals(10, color.getRed(), 1);
        assertEquals(50, color.getGreen(), 1);
        assertEquals(100, color.getBlue(), 1);
    }

    @Test
    public void writeImageByPath() throws IOException {
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, new Color(100, 50, 10).getRGB());
        ImageWriter writer = new ImageWriter();
        File file = new File("src/test/resources/images/write1.jpg");
        Image image = new Image(file, bufferedImage);
        writer.write(image, file);
        ImageReader reader = new ImageReader();
        Image readImage = reader.read(file);
        readImage.load();
        BufferedImage readImageBuffer = readImage.getBuffer();
        Color color = new Color(readImageBuffer.getRGB(0, 0));
        assertEquals(100, color.getRed(), 1);
        assertEquals(50, color.getGreen(), 1);
        assertEquals(10, color.getBlue(), 1);
    }
}
