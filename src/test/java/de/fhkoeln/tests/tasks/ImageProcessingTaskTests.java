package de.fhkoeln.tests.tasks;

import de.fhkoeln.Pipeline;
import de.fhkoeln.datatypes.Image;
import de.fhkoeln.tasks.ImageProcessingTask;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ImageProcessingTaskTests {
    private Color[][] imageMatrix = new Color[5][5];

    @Test
    public void imageProcessingTaskTest() throws IOException {
        imageMatrix[0][0] = new Color(75, 75, 75);
        imageMatrix[0][1] = new Color(71, 71, 71);
        imageMatrix[0][2] = new Color(99, 99, 99);
        imageMatrix[0][3] = new Color(72, 72, 72);
        imageMatrix[0][4] = new Color(75, 75, 75);

        imageMatrix[1][0] = new Color(155, 155, 155);
        imageMatrix[1][1] = new Color(29, 29, 29);
        imageMatrix[1][2] = new Color(72, 72, 72);
        imageMatrix[1][3] = new Color(29, 29, 29);
        imageMatrix[1][4] = new Color(150, 150, 150);


        imageMatrix[2][0] = new Color(223, 223, 223);
        imageMatrix[2][1] = new Color(177, 177, 177);
        imageMatrix[2][2] = new Color(155, 155, 155);
        imageMatrix[2][3] = new Color(223, 223, 223);
        imageMatrix[2][4] = new Color(177, 177, 177);

        imageMatrix[3][0] = new Color(151, 151, 151);
        imageMatrix[3][1] = new Color(75, 75, 75);
        imageMatrix[3][2] = new Color(29, 29, 29);
        imageMatrix[3][3] = new Color(76, 76, 76);
        imageMatrix[3][4] = new Color(100, 100, 100);

        imageMatrix[4][0] = new Color(100, 100, 100);
        imageMatrix[4][1] = new Color(155, 155, 155);
        imageMatrix[4][2] = new Color(224, 224, 224);
        imageMatrix[4][3] = new Color(177, 177, 177);
        imageMatrix[4][4] = new Color(150, 150, 150);

        ImageProcessingTask task = new ImageProcessingTask(1.0, 20, 0.8, true, false, false);
        Pipeline pipeline = new Pipeline("src/test/resources/images/tasks/processing/");
        pipeline.addTask(task);
        pipeline.addDocument("src/test/resources/images/test.jpg");
        pipeline.apply();

        File file = new File("src/test/resources/images/test.jpg");
        Image image = new Image(file);
        assertEquals(true, file.exists());

        image.load();
        image.makeGrayScale();
        BufferedImage buffer = image.getBuffer();
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
}
