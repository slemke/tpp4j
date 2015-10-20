package de.fhkoeln.tests;

import de.fhkoeln.Pipeline;
import de.fhkoeln.exceptions.OCRException;
import de.fhkoeln.tasks.ImageProcessingTask;
import de.fhkoeln.tasks.NLPTask;
import de.fhkoeln.tasks.OCRTask;
import de.fhkoeln.tasks.TextCleaningTask;
import org.junit.Test;

import java.util.Properties;

public class ShowcaseTest {

    @Test
    public void noExceptionTest() {
        String directory = "src/test/resources/images/input/";
        String document1 = "src/test/resources/images/showcase/";
        String workspace = "src/test/resources/images/showcase-output/";

        Properties prop = new Properties();
        prop.put("annotators", "tokenize, ssplit, pos, ner, lemma");
        prop.put("tokenize.language", "de");
        prop.put("ner.model", "edu/stanford/nlp/models/ner/german.hgc_175m_600.crf.ser.gz");
        prop.put("ner.applyNumericClassifiers", "false");
        prop.put("ner.useSUTime", "false");
        prop.put("pos.model", "edu/stanford/nlp/models/pos-tagger/german/german-hgc.tagger");

        OCRTask ocr = null;
        try {
            ocr = new OCRTask("deu");
        } catch (OCRException e) {
            e.printStackTrace();
        }

        ImageProcessingTask imp = new ImageProcessingTask(1.0, 10, 0.0, true, true, false);
        NLPTask nlpTask = new NLPTask(prop);
        TextCleaningTask cleaningTask = new TextCleaningTask("");

        Pipeline pipeline = new Pipeline(workspace);
        pipeline.addDocument(document1);
        System.out.println("Anzahl der Dokumente: " + pipeline.numberOfDocuments());
        pipeline.addTask(imp);
        pipeline.addTask(ocr);
        pipeline.addTask(cleaningTask);
        pipeline.addTask(nlpTask);

        pipeline.apply();

    }
}
