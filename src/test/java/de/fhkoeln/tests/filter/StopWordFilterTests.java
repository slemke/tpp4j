package de.fhkoeln.tests.filter;

import de.fhkoeln.datatypes.Token;
import de.fhkoeln.filter.StopWordFilter;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StopWordFilterTests {

    @Test
    public void removeStopwordsTest() throws IOException {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token("sagt"));
        tokens.add(new Token("über"));
        tokens.add(new Token("ziehen"));
        tokens.add(new Token("daraus"));

        StopWordFilter filter = new StopWordFilter("stopwords.txt");
        ArrayList<Token> result = filter.removeStopwords(tokens);
        assertEquals(true, result.isEmpty());
    }
}
