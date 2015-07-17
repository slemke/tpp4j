package de.fhkoeln.filter;

import de.fhkoeln.datatypes.Replacement;
import de.fhkoeln.io.TextReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class provides a filter to correct abbreviations in text. The Stanford Core NLP Tools sometimes incorrectly mark abbreviations as sentence endings.
 * With this filter you can replace abbreviations by their complete version (i.e. > "in other words").
 * This can introduce new errors if not used and tested correctly.
 * @author Sascha Lemke
 */
public class AbbreviationFilter extends ArrayList<Replacement> {

    /**
     * Creates a new instance of <code>AbbreviationFilter</code> containing all the corrections provided by a file. This file contains comma seperated values.
     * @param location The location of the file containing the abbreviations.
     * @throws IOException
     */
    public AbbreviationFilter(String location) throws IOException {
        File file = new File(location);
        TextReader reader = new TextReader();
        String abbreviations = reader.read(file);
        String[] lines = abbreviations.split("\\r?\\n");
        for(String line : lines) {
            String[] split = line.split(";");
            String replacement = "";
            if(split.length > 2) {
                throw new IllegalArgumentException("Couldn't read abbreviations from " + location);
            }
            if(split.length == 1)
                replacement = "";
            if(split.length == 2) {
                replacement = split[1];
            }
            split[0] = split[0].replace(".", "\\.");
            this.add(new Replacement(split[0], replacement));
        }
    }

    /**
     * Removes all the abbreviations from the provided text.
     * @param text The text from which the abbreviations are removed.
     * @return The corrected version of the text.
     */
    public String removeAbbreviations(String text) {
        for(Replacement abbr : this) {
            text = text.replaceAll(abbr.getValue(), abbr.getReplacement());
        }
        return text;
    }
}
