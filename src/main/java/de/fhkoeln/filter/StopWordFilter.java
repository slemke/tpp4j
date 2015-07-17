package de.fhkoeln.filter;

import de.fhkoeln.datatypes.Token;
import de.fhkoeln.io.TextReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class provides a filter to removed stop words from text. Stop words are words that don't provide deeper meaning to a text.
 * @author Sascha Lemke
 */
public class StopWordFilter extends ArrayList<String> {

    /**
     * Creates a new instance of <code>StopWordFilter</code> containing all the stop words provided by a file. This file contains comma seperated values.
     * @param path  The location of the file containing the stop words.
     * @throws IOException
     */
    public StopWordFilter(String path) throws IOException {
        TextReader reader = new TextReader();
        String read = reader.read(path);
        String[] words = read.split("\\r?\\n");
        for(String word : words) {
            if(!word.startsWith(";"))
                this.add(word);
        }
    }

    /**
     * Removes all the stop words from the provided text.
     * @param list A list containing the tokens from a text.
     * @return A list of tokens without stop words.
     */
    public ArrayList<Token> removeStopwords(ArrayList<Token> list) {
        for(Iterator<Token> iterator = list.iterator(); iterator.hasNext();) {
            Token token = iterator.next();
            if(this.contains(token.getToken()))
                iterator.remove();
        }
        return list;
    }
}
