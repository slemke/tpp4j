package de.fhkoeln.tests.io;

import de.fhkoeln.io.TextReader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TextReaderTests {

    @Test
    public void textReaderByFileTest() throws IOException {
        String input = "This is an example.\r\n";
        File file = new File("src/test/resources/text/output.txt");
        TextReader reader = new TextReader();
        String output = reader.read(file);
        assertEquals(input, output);
    }

    @Test
    public void textReaderByStringTest() throws IOException {
        String input = "This is an example.\r\n";
        TextReader reader = new TextReader();
        String output = reader.read("src/test/resources/text/output.txt");
        assertEquals(input, output);
    }

}
