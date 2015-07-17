package de.fhkoeln.tasks;

import de.fhkoeln.datatypes.Document;
import de.fhkoeln.datatypes.Replacement;
import de.fhkoeln.datatypes.Text;
import de.fhkoeln.exceptions.CleaningException;
import de.fhkoeln.exceptions.PipelineException;
import de.fhkoeln.io.TextReader;
import de.fhkoeln.io.TextWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * This task uses a set of rules to clean text. This primarily used to clean text after OCR to remove errors.
 * You can use the default rules, provide your own rules or use both. The rules must come as escaped rules in .csv format.
 * @author Sascha Lemke
 */
public final class TextCleaningTask extends Task {

    private ArrayList<Replacement> defaultPatterns = new ArrayList<Replacement>();
    private ArrayList<Replacement> userPatterns = new ArrayList<Replacement>();
    private final String PATTERNS = "cleaning.csv";
    private boolean useDefaultPatterns = true;
    private String patternLocation = "";

    /**
     * Creates a new Task for cleaning text in the <code>Pipeline</code>.
     * @param patternLocation The path to the *.csv file containing the user specified rules.
     */
    public TextCleaningTask(String patternLocation) {
        this.patternLocation = patternLocation;
    }

    /**
     * Creates a new Task for cleaning text in the <code>Pipeline</code>.
     * @param patternLocation The path to the *.csv file containing the user specified rules.
     * @param useDefaultPatterns Boolean value that decides if the default patterns are used or not.
     */
    public TextCleaningTask(String patternLocation, boolean useDefaultPatterns) {
        this.patternLocation = patternLocation;
        this.useDefaultPatterns = useDefaultPatterns;
    }

    /**
     * Adds a new cleaning pattern at runtime. Doesn't work if called after the <code>apply</code> method.
     * @param pattern The regex to find the cleaning part.
     * @param replace The "cleaned" version of the text.
     */
    public void addPattern(String pattern, String replace) {
        defaultPatterns.add(new Replacement(pattern, replace));
    }

    /**
     * This method enables or disables the use of the default pattern.
     * @param value Boolean value that decides if the default patterns are used or not.
     */
    public void useDefaultPatterns(boolean value) {
        this.useDefaultPatterns = value;
    }


    @Override
    public Document apply(Document document) throws PipelineException {
        if(!(document instanceof Text)) {
            getLogger().log(Level.WARNING, "Document is not a text file. Skipping!");
            return document;
        }

        Text text = (Text) document;
        File directory = new File(getWorkingDirectory() + File.separator + text.getFileName() + File.separator + "cleaned" + File.separator);
        if(!directory.exists() && !directory.mkdir())
            throw new CleaningException("Couldn't create directory for cleaned files! Can't write cleaned files to it!");

        File file = new File(getWorkingDirectory() + File.separator + text.getFileName() + File.separator + "ocr" + File.separator);
        File[] files = file.listFiles();
        StringBuilder sb = new StringBuilder();

        if(files != null) {
            for (File ocr : files) {
                try {
                    TextReader reader = new TextReader();
                    String data = reader.read(ocr);

                    if(!patternLocation.equals("")) {
                        File patternFile = new File(patternLocation);
                        try {
                            String output = reader.read(patternFile);
                            String[] lines = output.split("\\r?\\n");
                            String replacement = "";
                            for(String line : lines) {
                                String[] split = line.split(";");
                                if(split.length > 2) {
                                    throw new CleaningException("Pattern file uses a bad format. Use Expression;Replacement");
                                }
                                if(split.length == 1)
                                    replacement = "";
                                if(split.length == 2) {
                                    replacement = split[1];
                                }
                                userPatterns.add(new Replacement(split[0], replacement));
                            }
                        } catch (IOException e) {
                            throw new CleaningException("Couldn't read " + patternFile.getPath());
                        }
                    }

                    if(useDefaultPatterns) {
                        File patternFile = new File(PATTERNS);
                        try {
                            String output = reader.read(patternFile);
                            String[] lines = output.split("\\r?\\n");
                            String replacement = "";
                            for(String line : lines) {
                                String[] split = line.split(";");
                                if(split.length > 2) {
                                    throw new CleaningException("Pattern file uses a bad format. Use Expression;Replacement");
                                }
                                if(split.length == 1)
                                    replacement = "";
                                if(split.length == 2) {
                                    replacement = split[1];
                                }
                                defaultPatterns.add(new Replacement(split[0], replacement));
                            }
                        } catch (IOException e) {
                            throw new CleaningException("Couldn't read intern pattern file.");
                        }
                    }

                    if (useDefaultPatterns) {
                        if (!patternLocation.equals("")) {
                            for (Replacement defaultPattern : defaultPatterns) {
                                data = data.replaceAll(defaultPattern.getValue(), defaultPattern.getReplacement());
                            }
                            for (Replacement userPattern : userPatterns) {
                                data = data.replaceAll(userPattern.getValue(), userPattern.getReplacement());
                            }
                        } else {
                            for (Replacement defaultPattern : defaultPatterns) {
                                data = data.replaceAll(defaultPattern.getValue(), defaultPattern.getReplacement());
                            }
                        }
                    } else {
                        if (!patternLocation.equals("")) {
                            for (Replacement userPattern : userPatterns) {
                                data = data.replaceAll(userPattern.getValue(), userPattern.getReplacement());
                            }
                        } else
                            throw new CleaningException("Bad settings for TextCleaningTask! Couldn't find any rules!");
                    }
                    sb.append(data);
                    TextWriter writer = new TextWriter();
                    writer.write(data, directory.getPath() + File.separator + ocr.getName());
                } catch (IOException e) {
                    getLogger().log(Level.SEVERE, "Failed to get list of OCR files.", e);
                }
            }
        }
        text.setText(sb.toString());
        return text;
    }
}
