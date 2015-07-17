package de.fhkoeln.tasks;

import de.fhkoeln.datatypes.Document;
import de.fhkoeln.datatypes.Image;
import de.fhkoeln.datatypes.Text;
import de.fhkoeln.exceptions.OCRException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * This class defines the OCR Task using Tesseract. This class should be subclassed if you implement your own OCRProcess using Tesseract because this tasks checks for a correct installation.
 * @author Sascha Lemke
 */
public class OCRTask extends Task {

    private String language;

    /**
     * Creates a new task for OCR purposes and also checks if tesseract is installed by looking at the version via command line.
     * @param language The languages used for the OCRTask. Example for a single language: "deu"; More than one: "deu+deu-frak"
     * @throws OCRException
     */
    public OCRTask(String language) throws OCRException {
        this.language = language;

        String[] version = {"tesseract", "-v"};
        try {
            ProcessBuilder builder = new ProcessBuilder(version);
            builder.redirectErrorStream(true);
            Process versionProcess = builder.start();
            versionProcess.waitFor();
            int value = versionProcess.exitValue();

            if(value != 0) {
                throw new OCRException("Couldn't find Tesseract. Install Tesseract and/or update your environment variable.");
            } else {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(versionProcess.getInputStream()));
                String line = bufferedReader.readLine();
                getLogger().log(Level.INFO, "Found " + line);
            }
        } catch (IOException e) {
            throw new OCRException("Failede to test for Tesseract installation. IO Error occured.");
        } catch (InterruptedException e) {
            throw new OCRException("Failed to test for Tesseract installation. Process interrupted.");
        }
    }

    @Override
    public Document apply(Document document) throws OCRException {

        if(document instanceof Image) {
            Image image = (Image) document;
            Runtime rt = Runtime.getRuntime();

            String ocrPath = getWorkingDirectory() + File.separator + image.getName() + File.separator + "ocr" + File.separator;
            File directory = new File(getWorkingDirectory() + File.separator + image.getName());
            File ocrDirectory = new File(ocrPath);

            if(!directory.exists() && !directory.mkdir()) {
                getLogger().log(Level.SEVERE, "Couldn't create " + directory.getPath() +  "!");
                throw new OCRException("Couldn't create " + directory.getPath() +  "!");
            }

            if(!ocrDirectory.exists() && !ocrDirectory.mkdir()) {
                getLogger().log(Level.SEVERE, "Couldn't create " + ocrDirectory.getPath() + "!");
                throw new OCRException("Couldn't create " + ocrDirectory.getPath() + "!");
            }

            System.out.println("OCR begonnen: " + image.getName());

            try {
                String path = image.getPath();
                String destination = ocrPath + image.getName();
                String[] cmd = {"tesseract", path, destination, "-l", language};
                Process process = rt.exec(cmd);
                process.waitFor();
                process.destroy();
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Tesseract can't read image!", e);
            } catch (InterruptedException e) {
                getLogger().log(Level.SEVERE, "Image-to-Text Task interrupted!", e);
            }

            System.out.println("OCR für \"" + image.getName() + "\" beendet.");

            return image;
        } else if(document instanceof Text) {

            Text text = (Text) document;
            ArrayList<Image> images;
            Runtime rt = Runtime.getRuntime();

            if(text.getProcessedImages().size() != 0)
                images = text.getProcessedImages();
            else
                images = text.getImages();

            String ocrPath = getWorkingDirectory() + File.separator + text.getFileName() + File.separator + "ocr" + File.separator;
            File directory = new File(getWorkingDirectory() + File.separator + text.getFileName());
            File ocrDirectory = new File(ocrPath);

            if(!directory.exists() && !directory.mkdir()) {
                getLogger().log(Level.SEVERE, "Couldn't create " + directory.getPath() +  "!");
                throw new OCRException("Couldn't create " + directory.getPath() +  "!");
            }

            if(!ocrDirectory.exists() && !ocrDirectory.mkdir()) {
                getLogger().log(Level.SEVERE, "Couldn't create " + ocrDirectory.getPath() + "!");
                throw new OCRException("Couldn't create " + ocrDirectory.getPath() + "!");
            }

            System.out.println("OCR begonnen: " + text.getFileName());
            System.out.println("Anzahl der Seiten: " + text.numberOfImages());

            for(Image image : images) {
                try {
                    String path = image.getPath();
                    path = path.replace(File.separator, "/");
                    String destination = ocrPath + image.getName();
                    String[] cmd = {"tesseract", path, destination, "-l", language};
                    Process process = rt.exec(cmd);
                    process.waitFor();
                    process.destroy();
                } catch (IOException e) {
                    getLogger().log(Level.SEVERE, "Tesseract can't read image!", e);
                } catch (InterruptedException e) {
                    getLogger().log(Level.SEVERE, "Image-to-Text Task interrupted!", e);
                }
            }
            System.out.println("OCR für \"" + text.getFileName() + "\" beendet.");

            return text;
        } else {
            throw new OCRException("Document provided for OCRTask is not a valid document.");
        }
    }
}
