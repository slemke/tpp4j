package de.fhkoeln.tests.io;

import de.fhkoeln.datatypes.Image;
import de.fhkoeln.io.ImageReader;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ImageReaderTests {

    public Color imageMatrix[][] = new Color[5][5];

    public void setUp() {
        imageMatrix[0][0] = new Color(254, 0,0);
        imageMatrix[0][1] = new Color(144, 0, 253);
        imageMatrix[0][2] = new Color(253, 0, 212);
        imageMatrix[0][3] = new Color(144, 0, 255);
        imageMatrix[0][4] = new Color(253, 0,0);

        imageMatrix[1][0] = new Color(0, 255, 49);
        imageMatrix[1][1] = new Color(1, 1, 255);
        imageMatrix[1][2] = new Color(144, 0, 255);
        imageMatrix[1][3] = new Color(1, 0, 254);
        imageMatrix[1][4] = new Color(255, 126, 2);

        imageMatrix[2][0] = new Color(255, 251, 0);
        imageMatrix[2][1] = new Color(0, 255, 247);
        imageMatrix[2][2] = new Color(2, 255, 50);
        imageMatrix[2][3] = new Color(254, 251, 0);
        imageMatrix[2][4] = new Color(0, 254, 246);

        imageMatrix[3][0] = new Color(255, 128, 0);
        imageMatrix[3][1] = new Color(254, 0, 0);
        imageMatrix[3][2] = new Color(0, 1, 254);
        imageMatrix[3][3] = new Color(255, 1, 1);
        imageMatrix[3][4] = new Color(255, 0, 210);

        imageMatrix[4][0] = new Color(255, 0, 213);
        imageMatrix[4][1] = new Color(0, 255, 49);
        imageMatrix[4][2] = new Color(255, 253, 0);
        imageMatrix[4][3] = new Color(0, 255, 246);
        imageMatrix[4][4] = new Color(255, 127, 0);
    }


    @Test
    public void imageReaderByFileTest() throws IOException {

        File file = new File("src/test/resources/images/test.jpg");
        ImageReader reader = new ImageReader();
        Image image = reader.read(file);
        BufferedImage buffer = image.getBuffer();
        setUp();

        int height = buffer.getHeight();
        int width = buffer.getWidth();

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color color = new Color(buffer.getRGB(x, y));
                assertEquals(color.getRed(), imageMatrix[x][y].getRed(), 1);
                assertEquals(color.getGreen(), imageMatrix[x][y].getGreen(), 1);
                assertEquals(color.getBlue(), imageMatrix[x][y].getBlue(), 1);
            }
        }
    }

    @Test
    public void imageReaderByStringTest() throws IOException {

        ImageReader reader = new ImageReader();
        Image image = reader.read("src/test/resources/images/test.jpg");
        BufferedImage buffer = image.getBuffer();
        setUp();

        int height = buffer.getHeight();
        int width = buffer.getWidth();

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color color = new Color(buffer.getRGB(x, y));
                assertEquals(color.getRed(), imageMatrix[x][y].getRed(), 1);
                assertEquals(color.getGreen(), imageMatrix[x][y].getGreen(), 1);
                assertEquals(color.getBlue(), imageMatrix[x][y].getBlue(), 1);
            }
        }
    }

    @Test
    public void fileDoesntExistTest() {
        ImageReader reader = new ImageReader();
        try {
            reader.read("src/test/resources/images/test4.jpg");
            fail("Expected an IIOException to be thrown");
        } catch (IOException e) {
            assertThat(e.getMessage(), is("Can't read input file!"));
        }
    }
}
