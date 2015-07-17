package de.fhkoeln.tests.io;

import de.fhkoeln.io.TextReader;
import de.fhkoeln.io.TextWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TextWriterTests {

    @Test
    public void textWriterByFileTest() throws IOException {
        String input = "This is an example.\r\n";
        TextWriter writer = new TextWriter();
        File file = new File("src/test/resources/text/output.txt");
        writer.write(input, file);

        TextReader reader = new TextReader();
        String output = reader.read(file);
        assertEquals(input, output);
    }

    @Test
    public void textWriterByStringTest() throws IOException {
        String input = "This is an example.\r\n";
        TextWriter writer = new TextWriter();
        writer.write(input, "src/test/resources/text/output.txt");

        TextReader reader = new TextReader();
        String output = reader.read("src/test/resources/text/output.txt");
        assertEquals(input, output);
    }

}
