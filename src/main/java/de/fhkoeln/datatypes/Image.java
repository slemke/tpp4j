package de.fhkoeln.datatypes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class acts as a container for the image data that is used within the pipeline.
 * @author Sascha Lemke
 */
public class Image implements Document {

    private File file;
    private BufferedImage buffer = null;
    private static HashMap<Integer, Double> redMap = new HashMap<Integer, Double>();
    private static HashMap<Integer, Double> greenMap = new HashMap<Integer, Double>();
    private static HashMap<Integer, Double> blueMap = new HashMap<Integer, Double>();
    private String name;
    private String type;


    /**
     * Creates a new <code>Image</code> instance from a <code>File</code> object.
     * @param file
     */
    public Image(File file) {
        this.file = file;
        this.name = getFileName(file);
        this.type = getFileType(file);
    }

    /**
     * Creates a new <code>Image</code> instance from a <code>File</code> object and adds image data by providing a <code>BufferedImage</code>.
     * @param file
     * @param bufferedImage
     */
    public Image(File file, BufferedImage bufferedImage) {
        this.file = file;
        this.buffer = bufferedImage;
        this.name = getFileName(file);
        this.type = getFileType(file);
    }

    /**
     * Returns the image name.
     * @return the image name
     */
    public String getName() {
        return this.name;
    }

    private String getFileName(File file) {
        String name = file.getName();
        int i = name.indexOf(".");
        return name.substring(0, i);
    }

    private String getFileType(File file) {
        String name = file.getName();
        int i = name.indexOf(".");
        return name.substring(i, name.length());
    }

    /**
     * Returns the image type.
     * @return The image type.
     */
    public String getType(){
        return type;
    }

    /**
     * {@link java.io.File#getPath}
     * @return
     */
    public String getPath() {
        return file.getPath();
    }

    /**
     * Reads the image into buffer.
     * @throws IOException
     */
    public void load() throws IOException {
        buffer = ImageIO.read(file);
    }

    /**
     * Turns the image into a grayscale image.
     */
    public void makeGrayScale() {
        int height = buffer.getHeight();
        int width = buffer.getWidth();

        for(int i = 0; i <= 255; i++) {
            redMap.put(i, (i * 0.299));
            greenMap.put(i, (i * 0.587));
            blueMap.put(i, (i * 0.114));
        }

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color c = new Color(buffer.getRGB(x, y));
                double red = redMap.get(c.getRed());
                double green = greenMap.get(c.getGreen());
                double blue = blueMap.get(c.getBlue());
                int sum = (int) (red + green + blue);
                Color newColor = new Color(sum, sum, sum);
                buffer.setRGB(x, y, newColor.getRGB());
            }
        }
    }

    /**
     * Transforms the image into black and white.
     * @param threshold the threshold at which point a pixel turns into black or white. Value range between 0-1.
     */
    public void makeBinary(double threshold) {
        if(threshold < 0.0 && threshold > 1.0)
            throw new IllegalArgumentException("Incorrect value for threshold. Value range is 0.0-1.0.");

        int height = buffer.getHeight();
        int width = buffer.getWidth();
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color c = new Color(buffer.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                double color = ((red + green + blue) / 3.0) /255.0;

                Color n = new Color(255, 255, 255);
                if(color < threshold)
                    n = new Color(0, 0, 0);
                buffer.setRGB(x, y, n.getRGB());
            }
        }
    }

    /**
     * Changes the contrast and brightness of the image.
     * @param gain The value that increases the brightness of the image. Value range between 1.0-3.0.
     * @param bias The value that increases the contrast of the image. Value range between 0-50.
     */
    public void changeContrast(double gain, int bias) {
        if(gain < 1.0 && gain > 3.0)
            throw new IllegalArgumentException("Incorrect value for gain. Value range is 1.0-3.0.");

        if(bias < 0 && bias > 50)
            throw new IllegalArgumentException("Incorrect value for bias. Value range is 0-50");

        int height = buffer.getHeight();
        int width = buffer.getWidth();
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color c = new Color(buffer.getRGB(x, y));
                int red = (int) (gain * (c.getRed() + bias));
                if(red > 255) red = 255;
                if(red < 0) red = 0;
                int green = (int) (gain * (c.getGreen() + bias));
                if(green < 0) green = 0;
                if(green > 255) green = 255;
                int blue = (int) (gain * (c.getBlue() + bias));
                if(blue > 255) blue = 255;
                if(blue < 0) blue = 0;
                Color newColor = new Color(red, green, blue);
                buffer.setRGB(x, y, newColor.getRGB());
            }
        }
    }

    /**
     * Returns a <code>BufferedImage</code> containing the image data.
     * @return Returns a <code>BufferedImage</code> containing the image data or null, if not loaded into buffer.
     */
    public BufferedImage getBuffer() {
        return buffer;
    }
}
