package de.fhkoeln.tests.filter;

import de.fhkoeln.filter.AbbreviationFilter;
import de.fhkoeln.io.TextReader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AbbreviationFilterTests {

    @Test
    public void filterUntokenizedTextTest() throws IOException {

        String clean = "Dies ist ein Beispiel für die Nutzung von Abkürzungen (auch Abkürzung genannt). So muss dieser Text keinen Sinn ergeben, denn ein Doktor muss einen eingetragener Verein gründen. So ist unter Umständen das Gleichgewicht genauso lang wie ein Ungleichgewicht, zum Beispiel bei beziehungsweise zu Händen\r\n";

        File text = new File("src/test/resources/text/abbreviationFilter.txt");
        TextReader reader = new TextReader();
        String output = reader.read(text);
        AbbreviationFilter filter = new AbbreviationFilter("abbreviations.csv");
        String cleanText = filter.removeAbbreviations(output);
        assertEquals(clean, cleanText);
    }
}
