package de.fhkoeln.tasks;

import de.fhkoeln.datatypes.Document;
import de.fhkoeln.datatypes.Image;
import de.fhkoeln.datatypes.Text;
import de.fhkoeln.exceptions.ImageProcessingException;
import de.fhkoeln.exceptions.PipelineException;
import de.fhkoeln.io.ImageWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * This class defines the image processing task that is used to improve the image quality for the OCR Task.
 * @author Sascha Lemke
 */
public final class ImageProcessingTask extends Task {

    private int bias;
    private double gain;
    private double threshold;
    private boolean changeContrast = false;
    private boolean makeBinary = false;
    private boolean makeGrayScale = false;

    /**
     * Creates a new task for image processing.
     * @param gain The value that increases the brightness of the image. Value range between 1.0-3.0.
     * @param bias The value that increases the contrast of the image. Value range between 0-50.
     * @param threshold The threshold at which point a pixel turns into black or white. Value range between 0-1.
     */
    public ImageProcessingTask(double gain, int bias, double threshold, boolean makeGrayScale, boolean changeContrast, boolean makeBinary) {
        this.bias = bias;
        this.gain = gain;
        this.threshold = threshold;
        this.changeContrast = changeContrast;
        this.makeBinary = makeBinary;
        this.makeGrayScale = makeGrayScale;
    }

    @Override
    public Document apply(Document document) throws PipelineException{

        if(document instanceof Image) {
            Image image = (Image) document;
            File processDirectory = new File(getWorkingDirectory() + File.separator + image.getName() + File.separator  + "processed" + File.separator);
            System.out.println(processDirectory.toPath());

            if(!processDirectory.exists() && !processDirectory.mkdirs())
                throw new ImageProcessingException("Couldn't create working directory for processed images");


            if(image.getType().equals("tiff")) {
                getLogger().log(Level.WARNING, "Image processing for *.tiff files currently unsupported!");
                return image;
            }

            if(!processDirectory.exists() && !processDirectory.mkdir())
                throw new ImageProcessingException("Couldn't create processing directory!");

            try {
                image.load();
                if(makeGrayScale)
                    image.makeGrayScale();

                if(changeContrast)
                    image.changeContrast(gain, bias);

                if(makeBinary)
                    image.makeBinary(threshold);

                ImageWriter writer = new ImageWriter();
                File output = new File(processDirectory.getPath() + File.separator + image.getName() + image.getType());
                writer.write(image, output);

                return new Image(output);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Couldn't manipulate Image!");
            }
            return image;
        } else if(document instanceof Text) {
            Text text = (Text) document;
            ArrayList<Image> processedImages = new ArrayList<Image>();
            ImageWriter writer = new ImageWriter();

            File processDirectory = new File(getWorkingDirectory() + File.separator + text.getFileName() + File.separator  + "processed" + File.separator);
            if(!processDirectory.exists() && !processDirectory.mkdirs())
                throw new ImageProcessingException("Couldn't create working directory for processed images");

            for(Image image : text.getImages()) {
                try {
                    image.load();
                    if(makeGrayScale)
                        image.makeGrayScale();

                    if(changeContrast)
                        image.changeContrast(gain, bias);

                    if(makeBinary)
                        image.makeBinary(threshold);

                    File output = new File(getWorkingDirectory() + File.separator + text.getFileName() + File.separator + "processed" + File.separator + image.getName() + image.getType());
                    writer.write(image, output);
                    processedImages.add(new Image(output));
                } catch (IOException e) {
                    getLogger().log(Level.SEVERE, "Couldn't manipulate Image!", e);
                }
            }
            text.setProcessedImages(processedImages);
            return text;
        } else {
            throw new ImageProcessingException("Document provided for ImageProcessingTask is not a valid document.");
        }
    }
}
