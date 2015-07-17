package de.fhkoeln.tests.tasks;

import de.fhkoeln.Pipeline;
import de.fhkoeln.exceptions.OCRException;
import de.fhkoeln.io.TextReader;
import de.fhkoeln.tasks.ImageProcessingTask;
import de.fhkoeln.tasks.NLPTask;
import de.fhkoeln.tasks.OCRTask;
import de.fhkoeln.tasks.TextCleaningTask;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AllTasksTests {

    @Test
    public void allTasksTests() throws OCRException, IOException {
        String document2 = "src/test/resources/images/input/document2/";
        String document1 = "src/test/resources/images/input/document1/";
        String workspace = "src/test/resources/images/tasks/all/";

        Properties prop = new Properties();
        prop.put("annotators", "tokenize, ssplit, pos, ner, lemma");
        prop.put("tokenize.language", "de");
        prop.put("ner.model", "edu/stanford/nlp/models/ner/german.hgc_175m_600.crf.ser.gz");
        prop.put("ner.applyNumericClassifiers", "false");
        prop.put("ner.useSUTime", "false");
        prop.put("pos.model", "edu/stanford/nlp/models/pos-tagger/german/german-hgc.tagger");

        ImageProcessingTask imp = new ImageProcessingTask(1.0, 10, 0.0, true, true, false);
        OCRTask task = new OCRTask("deu");
        TextCleaningTask cleaningTask = new TextCleaningTask("");
        NLPTask nlpTask = new NLPTask(prop);

        Pipeline pipeline = new Pipeline(workspace);
        pipeline.addDocument(document1);
        pipeline.addDocument(document2);

        System.out.println("Anzahl der Dokumente: " + pipeline.numberOfDocuments());
        pipeline.addTask(imp);
        pipeline.addTask(task);
        pipeline.addTask(cleaningTask);
        pipeline.addTask(nlpTask);

        pipeline.apply();

        File ocr = new File("src/test/resources/images/tasks/all/document2/ocr/document2.txt");
        TextReader reader = new TextReader();
        String read = reader.read(ocr);
        assertEquals(true, ocr.exists());
        assertThat(read, not(""));


        File data = new File("src/test/resources/images/tasks/nlp/text/data.txt");
        String datatext = reader.read(data);
        assertEquals(true, data.exists());
        assertThat(datatext, not(""));

    }
}
