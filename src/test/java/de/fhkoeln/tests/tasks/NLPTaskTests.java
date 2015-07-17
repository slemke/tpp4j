package de.fhkoeln.tests.tasks;

import de.fhkoeln.Pipeline;
import de.fhkoeln.io.TextReader;
import de.fhkoeln.tasks.NLPTask;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class NLPTaskTests {

    @Test
    public void nlpTaskTests() throws IOException {
        Properties prop = new Properties();
        prop.put("annotators", "tokenize, ssplit, pos, ner, lemma");
        prop.put("tokenize.language", "de");
        prop.put("ner.model", "edu/stanford/nlp/models/ner/german.hgc_175m_600.crf.ser.gz");
        prop.put("ner.applyNumericClassifiers", "false");
        prop.put("ner.useSUTime", "false");
        prop.put("pos.model", "edu/stanford/nlp/models/pos-tagger/german/german-hgc.tagger");

        Pipeline pipeline = new Pipeline("src/test/resources/images/tasks/nlp/");
        NLPTask task = new NLPTask(prop);
        pipeline.addDocument("src/test/resources/text/list/text.txt");

        System.out.println("Anzahl der Dokumente: " + pipeline.numberOfDocuments());
        pipeline.addTask(task);
        pipeline.apply();

        File data = new File("src/test/resources/images/tasks/nlp/text/data.txt");
        TextReader reader = new TextReader();
        String read = reader.read(data);
        assertEquals(true, data.exists());
        assertThat(read, not(""));
    }

}
